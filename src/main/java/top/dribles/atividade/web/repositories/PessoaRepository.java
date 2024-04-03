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
    
    public Pessoa getPessoaById(int id) throws SQLException {
        String query = "SELECT * FROM Pessoa WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("ID"));
                    pessoa.setEndereco_id(rs.getInt("ENDERECO_ID"));
                    pessoa.setNome(rs.getString("NOME"));
                    pessoa.setEmail(rs.getString("EMAIL"));
                    pessoa.setTelefone(rs.getString("TELEFONE"));
                    pessoa.setCpf(rs.getString("CPF"));
                    return pessoa;
                }
            }
        }
    
        return null;
    }
    
    public Pessoa adicionarPessoa(Pessoa pessoa) throws SQLException {
        String query = 
                "INSERT INTO Pessoa (ENDERECO_ID, NOME, EMAIL, TELEFONE, CPF) "
                + "VALUES(?, ?, ?, ?, ?);";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            ps = conn.prepareStatement(query, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pessoa.getEndereco_id());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getEmail());
            ps.setString(4, pessoa.getTelefone());
            ps.setString(5, pessoa.getCpf());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            pessoa.setId(rs.getInt(1));
            
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        }
        
        return pessoa;
    }
    
    public void atualizarPessoa(Pessoa pessoa, String logradouro, String numero, 
            String complemento, String bairro, String cidade, String uf, String cep)
            throws SQLException {
        
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        Endereco endereco = enderecoRepository.getEnderecoById(pessoa.getEndereco_id());
        
        if (endereco != null) {
            endereco.setLogradouro(logradouro);
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);
            endereco.setBairro(bairro);
            endereco.setCidade(cidade);
            endereco.setUf(uf);
            endereco.setCep(cep);
        
            enderecoRepository.atualizarEndereco(endereco);
        }
        
        String query = "UPDATE Pessoa SET NOME = ?, TELEFONE = ? WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getTelefone());
            ps.setInt(3, pessoa.getId());
        
            ps.executeUpdate();
        }
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
