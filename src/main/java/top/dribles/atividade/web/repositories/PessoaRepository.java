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
                    
                    Endereco endereco = enderecoRepository.getEnderecoById(rs.getInt("ENDERECO_ID"));
                    pessoa.setEndereco(endereco);
                    
                    return pessoa;
                } else {
                    throw new IllegalArgumentException("Pessoa nao encontrada!");
                }
            }
        }
    }
    
    public Pessoa adicionarPessoa(Pessoa pessoa) throws SQLException {
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        
        if (pessoa.getEmail() == null || pessoa.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }
        
        if (pessoa.getTelefone() == null || pessoa.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório.");
        }
        
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()) {
            throw new IllegalArgumentException("Cpf é obrigatório.");
        }
        
        Endereco endereco= enderecoRepository.adicionarEndereco(pessoa.getEndereco());
        
        try {
            if (endereco != null && endereco.getId() > 0) {
                String query = 
                    "INSERT INTO Pessoa (ENDERECO_ID, NOME, EMAIL, TELEFONE, CPF) "
                    + "VALUES(?, ?, ?, ?, ?);";
            
                try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, endereco.getId());
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
    
    public void atualizarPessoa(int idAtt, Pessoa pessoa) throws SQLException {
//        Endereco endereco = enderecoRepository.getEnderecoById(pessoa.getEndereco().getId());
//        enderecoRepository.atualizarEndereco(endereco);

        Pessoa pessoaAtt = getPessoaById(idAtt);
        int idEnderecoAtt = pessoaAtt.getEndereco().getId();
        
        enderecoRepository.atualizarEndereco(idEnderecoAtt, pessoa.getEndereco());
        
        String query = "UPDATE Pessoa SET NOME = ?, TELEFONE = ? WHERE ID = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getTelefone());
            ps.setInt(3, idAtt);
        
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao inserir Pessoa no banco de dados!");
        }
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
