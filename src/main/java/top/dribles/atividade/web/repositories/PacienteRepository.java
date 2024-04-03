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
import top.dribles.atividade.web.model.Paciente;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;

/**
 *
 * @author crist
 */
public class PacienteRepository {
    private final Connection conn;
    
    public PacienteRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public PessoaRepository pessoaRepository = new PessoaRepository();
    
    public ArrayList<Paciente> getAllPacientes() throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM Paciente";
    
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("ID"));
                paciente.setPessoa_id(rs.getInt("PESSOA_ID"));
            }
        }
    
        return pacientes;
    }
    
    public Paciente getPacienteById(int id) throws SQLException {
        String query = "SELECT * FROM Paciente WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setId(rs.getInt("ID"));
                    paciente.setPessoa_id(rs.getInt("PESSOA_ID"));
                    paciente.setIs_active(rs.getBoolean("IS_ACTIVE"));
                    return paciente;
                }
            }
        }
    
        return null;
    }
    
    public Paciente adicionarPaciente(Paciente paciente, Pessoa pessoa, Endereco endereco) throws SQLException {
        
        Pessoa pessoaAdd = pessoaRepository.adicionarPessoa(pessoa, endereco);
        
        try {
            if (pessoaAdd != null && pessoaAdd.getId() > 0) {
                String query = "INSERT INTO Paciente (PESSOA_ID) VALUES (?);";

                try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, pessoaAdd.getId());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            paciente.setId(rs.getInt(1));
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Erro ao inserir Pessoa no banco de dados!");
            }
        } catch (SQLException e) {
            throw e;
        }
        
        return paciente;
    }
    
//    public void atualizarPaciente(Paciente paciente) throws SQLException {
//        String query = "UPDATE Endereco SET LOGRADOURO = ?, NUMERO = ?, "
//                + "COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, UF = ?, CEP = ? "
//                + "WHERE ID = ?";
//    
//        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, endereco.getLogradouro());
//            ps.setString(2, endereco.getNumero());
//            ps.setString(3, endereco.getComplemento());
//            ps.setString(4, endereco.getBairro());
//            ps.setString(5, endereco.getCidade());
//            ps.setString(6, endereco.getUf());
//            ps.setString(7, endereco.getCep());
//            ps.setInt(8, endereco.getId());
//        
//            ps.executeUpdate();
//        }
//    }
    
    public void deletarPaciente(int id) throws SQLException {
        String query = "UPDATE Paciente SET IS_ACTIVE = false "
                + "WHERE ID = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            
            ps.executeUpdate();
        }
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
