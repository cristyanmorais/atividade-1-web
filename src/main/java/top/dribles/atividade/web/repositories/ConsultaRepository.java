/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import top.dribles.atividade.web.infrastructure.ConnectionFactory;
import top.dribles.atividade.web.model.Paciente;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.model.Consulta;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 *
 * @author crist
 */
public class ConsultaRepository {
    private final Connection conn;
    
    public ConsultaRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public Consulta getConsultaById(int id) throws SQLException {
        String query = "SELECT * FROM Consulta WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setId(rs.getInt("ID"));
                    consulta.setMedico_id(rs.getInt("MEDICO_ID"));
                    consulta.setPaciente_id(rs.getInt("PACIENTE_ID"));
                    consulta.setData(rs.getDate("DATA_HORA"));
                    consulta.setIs_active(rs.getBoolean("IS_ACTIVE"));
//                    consulta.setHorario(rs.getString("DATA_HORA"));
                // Obtém o objeto Timestamp do banco de dados
                java.sql.Timestamp timestamp = rs.getTimestamp("DATA_HORA");
                
                // Converte o Timestamp para um objeto Date
                java.util.Date date = new java.util.Date(timestamp.getTime());
                
                // Formata apenas a parte de hora e minutos
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
                String horaMinutos = sdf.format(date);
                
                consulta.setHorario(horaMinutos);
                
                // Defina a data completa (incluindo data e hora) como um objeto java.util.Date
                consulta.setData(date);
                    return consulta;
                }
            } 
        }
    
        return null;
    }
    
    public Consulta adicionarConsulta(Consulta consulta) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = 
                "INSERT INTO Consulta (MEDICO_ID, PACIENTE_ID, DATA_HORA) "
                + "VALUES(?, ?, ?);";
        
    // ------------------------  Medico Aleatorio  --------------------------- 
        System.out.println("medico return: " + consulta.getMedico_id());
        
        if(consulta.getMedico_id() == 0) {
            String qAux = "SELECT * FROM Medico ORDER BY RANDOM() LIMIT 1";
            
            try {
                ps = conn.prepareStatement(qAux);
                rs = ps.executeQuery();
                
                if(rs.next()) {
                    consulta.setMedico_id(rs.getInt("ID"));
                }
            }catch (SQLException e) {
                throw new IllegalArgumentException("Erro buscando Random");
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException("Erro fechando Random!");
                }
            }
            
            
        }
        
    // --------------------  Horario e Dia da Consulta  ----------------------
        Date data = consulta.getData();
        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        String horario = consulta.getHorario();
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        
        LocalTime localTime = LocalTime.of(hora, minutos);
        LocalDateTime dataHora = localDate.atTime(localTime);
        
        System.out.println("dataHora: " + dataHora);
        
        Timestamp timestamp = Timestamp.valueOf(dataHora);
        
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        
        if (consulta.getData() == null) {
            throw new IllegalArgumentException("consulta.getData() = null");
        }
        
        if (diaSemana == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("A consulta nao pode ser marcada para domingo.");
        } else if (hora < 07 && hora > 19) {
            throw new IllegalArgumentException("O horario da consulta deve estar entre 07:00 e 19:00.");
        }
        
    // --------------------  Paciente e Medico Inativo  -----------------------
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.getPacienteById(consulta.getPaciente_id());
        boolean pacienteIsActive = paciente.getIs_active();
        
        MedicoRepository medicoRepository = new MedicoRepository();
        Medico medico = medicoRepository.getMedicoById(consulta.getMedico_id());
        boolean medicoIsActive = medico.getIs_active();
        
        if(!pacienteIsActive) {
            throw new IllegalArgumentException("Paciente nao encontrado!");
        }
        
        if(!medicoIsActive) {
            throw new IllegalArgumentException("Medico nao encontrado!");
        }
        
    // --------------------  Disponibilidade de Horario  ----------------------
        if(!horarioDisponivelMedico(timestamp, consulta.getMedico_id())) {
            throw new IllegalArgumentException("Horario Nao esta Disponível!");
        }
        
        if(!dataDisponivelPaciente(timestamp, consulta.getPaciente_id())) {
            throw new IllegalArgumentException("Paciente ja tem Consulta nessa Data!");
        }
        
        Timestamp timestampAtual = new Timestamp(System.currentTimeMillis());
        timestampAtual.setTime(timestampAtual.getTime() + 1800000);
        
        if(timestamp.getTime() < timestampAtual.getTime()) {
            throw new IllegalArgumentException("A consulta precisa ser agendada a partir de meia hora do horario atual!");
        }
        
    // -------------------------------  Query  --------------------------------
        
        try {
//            java.sql.Date dataHoraSQL = new java.sql.Date(consulta.getData_hora());
//            LocalDateTime dataHoraConsulta = consulta.getData();

            ps = conn.prepareStatement(query, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, consulta.getMedico_id());
            ps.setInt(2, consulta.getPaciente_id());
            ps.setTimestamp(3, timestamp);
            
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            consulta.setId(rs.getInt(1));
            
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new IllegalArgumentException("Erro fechando Add Consulta!");
            }
        }
        return consulta;
    }
    
    public void cancelarConsulta(int idCancelamento, int idConsulta) throws SQLException {
        String query = "UPDATE Consulta SET cancelamento_id = ?, is_active = false "
                + "WHERE ID = ?";
        
        if(idConsulta <= 0) {
            throw new IllegalArgumentException("Necessario informar consulta!");
        }
        
        if(idCancelamento <= 0) {
            throw new IllegalArgumentException("Necessario informar motivo de cancelamento!");
        }
        
        Consulta consulta = getConsultaById(idConsulta);
        
        if(!consulta.getIs_active()) {
            throw new IllegalArgumentException("Consulta ja cancelada");
        }
        
//   -----------------------------------------------------------------------------           
        
        Date data = consulta.getData();
        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        String horario = consulta.getHorario();
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        
        LocalTime localTime = LocalTime.of(hora, minutos);
        LocalDateTime dataHora = localDate.atTime(localTime);
        
        Timestamp tsAtual = new Timestamp(System.currentTimeMillis());
        Timestamp tsConsulta =  Timestamp.valueOf(dataHora);
//        timestampAtual.setTime(timestampAtual.getTime() + 1800000);

        Duration diff = Duration.between(tsAtual.toLocalDateTime(), tsConsulta.toLocalDateTime());
        
        if (diff.toHours() < 24) {
            throw new IllegalArgumentException("Consulta nao pode ser cancelada faltando 24 horas ou menos");
        }

//        System.out.println("| tsAtual: " + tsAtual.getTime());
//        System.out.println("| tsConsulta: " + tsConsulta.getTime());

//  -----------------------------------------------------------------------------      
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCancelamento);
            ps.setInt(2, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
    
//  ---------------------------------  Aux  -----------------------------------
    
//    public LocalDateTime convertDateToLocalDateTime(Date date) {
//        if (date == null) {
//            throw new IllegalArgumentException("convertDateToLocalDateTime: date = null");
//        }
//        
//        Timestamp timestamp = new Timestamp(date.getTime());
//    
//        return timestamp.toLocalDateTime();
//    }
    
//    public int randomMedico() {
//        Medico medico = null;
//        
//        String query = "SELECT * FROM Medico ORDER BY RAND() LIMIT 1";
//        
//        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            try (ResultSet rs = ps.executeQuery()) {
//                if(rs.next()) {
//                    medico.setId(rs.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            throw new IllegalArgumentException("doidera braba");
//        } 
//        
//        return medico.getId();
//    }
    
    public boolean horarioDisponivelMedico(Timestamp dataHora, int medico_id) {
        
        if (dataHora == null) {
            throw new IllegalArgumentException("horarioDisponivelMedico: dataHora = null");
        }
        
        String query = "SELECT * FROM Consulta WHERE (data_hora = ? OR (data_hora > ? AND data_hora <= ?)) AND medico_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, dataHora);
            ps.setTimestamp(2, dataHora);
            ps.setTimestamp(3, new Timestamp(dataHora.getTime() + 360000));
            ps.setInt(4, medico_id);
        
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean dataDisponivelPaciente(Timestamp dataHora, int paciente_id) {
        if (dataHora == null) {
            throw new IllegalArgumentException("dataDisponivelPaciente: dataHora = null");
        }
        
        String query = "SELECT * FROM Consulta WHERE DATE(data_hora) = DATE(?) AND paciente_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, new java.sql.Date(dataHora.getTime()));
            ps.setInt(2, paciente_id);
    
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
