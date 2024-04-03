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
import java.time.LocalDateTime;
import java.time.LocalTime;

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
                    consulta.setData_hora(rs.getDate("DATA_HORA"));
                    return consulta;
                }
            }
        }
    
        return null;
    }
    
    public Consulta adicionarConsulta(Consulta consulta) throws SQLException {
        
    // --------------------  Horário e Dia da Consulta  ----------------------
        Date dataHoraConsulta = consulta.getData_hora();
        LocalDateTime dataHora = convertDateToLocalDateTime(dataHoraConsulta);
        
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        LocalTime horaConsulta = dataHora.toLocalTime();
        
        
        if (diaSemana == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("A consulta não pode ser marcada para domingo.");
        } else if (horaConsulta.isBefore(LocalTime.of(7, 0)) || horaConsulta.isAfter(LocalTime.of(19, 0))) {
            throw new IllegalArgumentException("O horário da consulta deve estar entre 07:00 e 19:00.");
        }
        
    // --------------------  Paciente e Medico Inativo  -----------------------
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.getPacienteById(consulta.getPaciente_id());
        boolean pacienteIsActive = paciente.getIs_active();
        
        MedicoRepository medicoRepository = new MedicoRepository();
        Medico medico = medicoRepository.getMedicoById(consulta.getMedico_id());
        boolean medicoIsActive = medico.getIs_active();
        
        if(!pacienteIsActive) {
            throw new IllegalArgumentException("Paciente não encontrado!");
        }
        
        if(!medicoIsActive) {
            throw new IllegalArgumentException("Medico não encontrado!");
        }
        
    // --------------------  Disponibilidade de Horario  ----------------------
        if(!horarioDisponivelMedico(consulta.getData_hora(), consulta.getMedico_id())) {
            throw new IllegalArgumentException("Horario Não está Disponível!");
        }
        
        if(!dataDisponivelPaciente(consulta.getData_hora(), consulta.getPaciente_id())) {
            throw new IllegalArgumentException("Paciente já tem Consulta nessa Data!");
        }
        
        Timestamp data_horaTS = new Timestamp(consulta.getData_hora().getTime());
        Timestamp data_atualTS = new Timestamp(System.currentTimeMillis());
        data_atualTS.setTime(data_atualTS.getTime() + 1800000);
        
        if(data_horaTS.getTime() >= data_atualTS.getTime()) {
            throw new IllegalArgumentException("A consulta precisa ser agendada a partir de meia hora do horário atual!");
        }
        
    // -------------------------------  Query  --------------------------------
        String query = 
                "INSERT INTO Consulta (MEDICO_ID, PACIENTE_ID, DATA_HORA, MOTIVO_CANCELAMENTO_ID) "
                + "VALUES(?, ?, ?, ?);";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            java.sql.Date dataHoraSQL = new java.sql.Date(consulta.getData_hora().getTime());
            ps = conn.prepareStatement(query, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, consulta.getMedico_id());
            ps.setInt(2, consulta.getPaciente_id());
            ps.setInt(3, consulta.getMotivo_cancelamento_id());
            ps.setDate(4, dataHoraSQL);
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            consulta.setId(rs.getInt(1));
            
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        }
        
        return consulta;
    }
    
    public void cancelarConsulta(int id) throws SQLException {
        
    }
    
//  ---------------------------------  Aux  -----------------------------------
    
    public LocalDateTime convertDateToLocalDateTime(Date date) {
        // Convertendo para java.sql.Timestamp
        Timestamp timestamp = new Timestamp(date.getTime());
    
        // Convertendo para LocalDateTime
        return timestamp.toLocalDateTime();
    }
    
    public boolean horarioDisponivelMedico(Date dataHora, int medico_id) {
        
        String query = "SELECT * FROM Consulta WHERE (data_hora = ? OR (data_hora > ? AND data_hora <= ?)) AND medico_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            // Define o parâmetro da consulta com a data e hora fornecidas
            ps.setTimestamp(1, new Timestamp(dataHora.getTime()));
            ps.setTimestamp(2, new Timestamp(dataHora.getTime()));
            ps.setTimestamp(3, new Timestamp(dataHora.getTime() + 3599999)); // Adiciona 1 hora à data fornecida
            ps.setInt(4, medico_id);
        
            // Executa a consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Verifica se algum resultado foi retornado
                if (rs.next()) {
                    // Horário não está disponível
                    return false;
                } else {
                    // Horário está disponível
                    return true;
                }
            }
        } catch (SQLException e) {
            // Lida com exceções de SQL, se houver
            e.printStackTrace();
            // Se ocorrer uma exceção, assume-se que o horário não está disponível por segurança
            return false;
        }
    }
    
    public boolean dataDisponivelPaciente(Date dataHora, int paciente_id) {
        String query = "SELECT * FROM Consulta WHERE DATE(data_hora) = DATE(?) AND paciente_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            // Define o parâmetro da consulta com o dia da data fornecida
            ps.setDate(1, new java.sql.Date(dataHora.getTime()));
            ps.setInt(2, paciente_id);
    
            // Executa a consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Verifica se algum resultado foi retornado
                return !rs.next(); // Retorna verdadeiro se não houver resultados (horário disponível)
            }
        } catch (SQLException e) {
            // Lida com exceções de SQL, se houver
            e.printStackTrace();
            // Se ocorrer uma exceção, assume-se que o horário não está disponível por segurança
            return false;
        }
    }
}
