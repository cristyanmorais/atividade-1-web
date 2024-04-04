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
    
    public ArrayList<Medico> getAllMedicos() throws SQLException {
        ArrayList<Medico> medicos = new ArrayList<>();
        String query = "SELECT * FROM Medico WHERE is_active = true;";
    
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("ID"));
                medico.setCrm(rs.getString("CRM"));
                medico.setIs_active(rs.getBoolean("IS_ACTIVE"));
                
                Pessoa pessoa = pessoaRepository.getPessoaById(medico.getPessoa().getId());
                medico.setPessoa(pessoa);
                
                medicos.add(medico);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao Buscar Medicos!");
        }
    
        return medicos;
    }
    
    public Medico getMedicoById(int id) throws SQLException {
        String query = "SELECT * FROM Medico WHERE ID = ? AND is_active = true";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico();
                    medico.setId(rs.getInt("ID"));
                    medico.setCrm(rs.getString("CRM"));
                    medico.setIs_active(rs.getBoolean("IS_ACTIVE"));
                    
                    Pessoa pessoa = pessoaRepository.getPessoaById(medico.getPessoa().getId());
                    medico.setPessoa(pessoa);
                
                    return medico;
                } else {
                    throw new IllegalArgumentException("Médico não encontrado!");
                }
            }
        }
    }
    
    public Medico adicionarMedico(Medico medico, Pessoa pessoa, Endereco endereco) throws SQLException {
        
        Pessoa pessoaAdd = pessoaRepository.adicionarPessoa(pessoa, endereco);
        
        try {
            if (pessoaAdd != null && pessoaAdd.getId() > 0) {
                String query = "INSERT INTO Medico (PESSOA_ID, ESPECIALIDADE_ID, CRM) VALUES (?, ?, ?);";

                try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, pessoaAdd.getId());
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
    
    public void atualizarMedico(Medico medico, Pessoa pessoa, Endereco endereco) throws SQLException {
        boolean pessoaAtt = pessoaRepository.atualizarPessoa(pessoa, endereco);
    
        if (!pessoaAtt) {
            throw new IllegalArgumentException("Erro ao atualizar Médico no banco de dados!");
        }
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
