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
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;

/**
 *
 * @author crist
 */
public class PessoaRepository {
    
    private final Connection conn;
    
    public PessoaRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public EnderecoRepository enderecoRepository = new EnderecoRepository();
    
    public Pessoa getPessoaById(int id) throws SQLException {
        String query = "SELECT * FROM Pessoa WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("ID"));
                    pessoa.setNome(rs.getString("NOME"));
                    pessoa.setEmail(rs.getString("EMAIL"));
                    pessoa.setTelefone(rs.getString("TELEFONE"));
                    pessoa.setCpf(rs.getString("CPF"));
                    
                    Endereco endereco = enderecoRepository.getEnderecoById(pessoa.getEndereco().getId());
                    pessoa.setEndereco(endereco);
                    
                    return pessoa;
                } else {
                    throw new IllegalArgumentException("Pessoa não encontrado!");
                }
            }
        }
    }
    
    public Pessoa adicionarPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
        
        Endereco enderecoAdd = enderecoRepository.adicionarEndereco(endereco);
        
        try {
            if (enderecoAdd != null && endereco.getId() > 0) {
                String query = 
                    "INSERT INTO Pessoa (ENDERECO_ID, NOME, EMAIL, TELEFONE, CPF) "
                    + "VALUES(?, ?, ?, ?, ?);";
            
                try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, enderecoAdd.getId());
                    ps.setString(2, pessoa.getNome());
                    ps.setString(3, pessoa.getEmail());
                    ps.setString(4, pessoa.getTelefone());
                    ps.setString(5, pessoa.getCpf());
                    ps.executeUpdate();
                
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            pessoa.setId(rs.getInt(1));
                        }
                    }
                } 
            } else {
                throw new IllegalArgumentException("Erro ao inserir Endereco no banco de dados!");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao inserir Pessoa no banco de dados!");
        }
        
        return pessoa;
    }
    
    public boolean atualizarPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
        boolean enderecoAtt = enderecoRepository.atualizarEndereco(endereco);
        
        String query = "UPDATE Pessoa SET NOME = ?, TELEFONE = ? WHERE ID = ?";
        
        if (enderecoAtt) {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, pessoa.getNome());
                ps.setString(2, pessoa.getTelefone());
                ps.setInt(3, pessoa.getId());
        
                ps.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
        
        return true;
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
