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
import java.util.ArrayList;
import top.dribles.atividade.web.infrastructure.ConnectionFactory;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.model.Especialidade;

/**
 *
 * @author crist
 */
public class MedicoRepository {
    private final Connection conn;
    
    public MedicoRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public PessoaRepository pessoaRepository = new PessoaRepository();
    public EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();
    
    public ArrayList<Medico> getAllMedicos() throws SQLException {
        ArrayList<Medico> medicos = new ArrayList<>();
//        String query = "SELECT * FROM Medico WHERE is_active = true";
            String query = "SELECT m.*, p.nome AS pessoa_nome, p.email AS pessoa_email " +
                   "FROM Medico m " +
                   "INNER JOIN Pessoa p ON m.pessoa_id = p.id " +
                   "WHERE m.is_active = true " +
                   "ORDER BY p.nome ASC"; 
    
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("ID"));
                medico.setCrm(rs.getString("CRM"));
                medico.setIs_active(rs.getBoolean("IS_ACTIVE"));
                medico.setLista_nome(rs.getString("pessoa_nome"));
                medico.setLista_email(rs.getString("pessoa_email"));
                
//                Pessoa pessoa = pessoaRepository.getPessoaById(rs.getInt("PESSOA_ID"));
//                medico.setPessoa(pessoa);
//                medico.setLista_nome(pessoa.getNome());
//                medico.setLista_email(pessoa.getEmail());
                
                Especialidade especialidade = especialidadeRepository.getEspecialidadeById(rs.getInt("ESPECIALIDADE_ID"));
                medico.setLista_especialidade(especialidade.getNome());
                
                medicos.add(medico);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao Buscar Medicos!");
        }
    
        return medicos;
    }
    
    public Medico getMedicoById(int id) throws SQLException {
        String query = "SELECT * FROM Medico WHERE ID = ?;";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico();
                    medico.setId(rs.getInt("ID"));
                    medico.setCrm(rs.getString("CRM"));
                    medico.setIs_active(rs.getBoolean("IS_ACTIVE"));
                    
                    if(medico.getIs_active()) {
                        Pessoa pessoa = pessoaRepository.getPessoaById(rs.getInt("PESSOA_ID"));
                        medico.setPessoa(pessoa);
                    } else {
                        throw new IllegalArgumentException("Medico Inativo!");
                    }
                        
                    return medico;
                } else {
                    throw new IllegalArgumentException("Médico não encontrado!");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao Buscar Medico");
        }
    }
    
    public Medico adicionarMedico(Medico medico) throws SQLException {
        if (medico.getEspecialidade() == null || medico.getEspecialidade().getId() <= 0) {
            throw new IllegalArgumentException("Especialidade inválida para o médico.");
        }
    
        if (medico.getCrm() == null || medico.getCrm().isEmpty()) {
            throw new IllegalArgumentException("CRM do médico é obrigatório.");
        }
    
        Pessoa pessoa = pessoaRepository.adicionarPessoa(medico.getPessoa());
        
        try {
            if (pessoa != null && pessoa.getId() > 0) {
                String query = "INSERT INTO Medico (PESSOA_ID, ESPECIALIDADE_ID, CRM) VALUES (?, ?, ?);";

                try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, pessoa.getId());
                    ps.setInt(2, medico.getEspecialidade().getId());
                    ps.setString(3, medico.getCrm());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            medico.setId(rs.getInt(1));
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Erro ao inserir Pessoa no banco de dados!");
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return medico;
    }
    
    public void atualizarMedico(int idAtt, Medico medico) throws SQLException {
        Medico medicoAtt = getMedicoById(idAtt);
        int idPessoaAtt = medicoAtt.getPessoa().getId();
        
        pessoaRepository.atualizarPessoa(idPessoaAtt, medico.getPessoa());
    }
    
    public void deletarMedico(int id) throws SQLException {
        String query = "UPDATE Medico SET IS_ACTIVE = false "
                + "WHERE ID = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao deletar Médico!");
        }
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
