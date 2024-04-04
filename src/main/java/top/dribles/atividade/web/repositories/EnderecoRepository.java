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
import top.dribles.atividade.web.model.Endereco;

/**
 *
 * @author crist
 */
public class EnderecoRepository {
    
    private final Connection conn;
    
    public EnderecoRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public ArrayList<Endereco> getAllEnderecos() throws SQLException {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        String query = "SELECT * FROM Endereco";
    
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("ID"));
                endereco.setLogradouro(rs.getString("LOGRADOURO"));
                endereco.setNumero(rs.getString("NUMERO"));
                endereco.setComplemento(rs.getString("COMPLEMENTO"));
                endereco.setBairro(rs.getString("BAIRRO"));
                endereco.setCidade(rs.getString("CIDADE"));
                endereco.setUf(rs.getString("UF"));
                endereco.setCep(rs.getString("CEP"));
                enderecos.add(endereco);
            }
        }
    
        return enderecos;
    }
    
    public Endereco getEnderecoById(int id) throws SQLException {
        String query = "SELECT * FROM Endereco WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(rs.getInt("ID"));
                    endereco.setLogradouro(rs.getString("LOGRADOURO"));
                    endereco.setNumero(rs.getString("NUMERO"));
                    endereco.setComplemento(rs.getString("COMPLEMENTO"));
                    endereco.setBairro(rs.getString("BAIRRO"));
                    endereco.setCidade(rs.getString("CIDADE"));
                    endereco.setUf(rs.getString("UF"));
                    endereco.setCep(rs.getString("CEP"));
                    return endereco;
                }
            }
        }
    
        return null;
    }
    
    public Endereco adicionarEndereco(Endereco endereco) throws SQLException {
        String query = 
                "INSERT INTO Endereco (LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, UF, CEP) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            ps = conn.prepareStatement(query, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, endereco.getLogradouro());
            ps.setString(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getUf());
            ps.setString(7, endereco.getCep());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            endereco.setId(rs.getInt(1));
            
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        }
        
        return endereco;
    }
    
    public void atualizarEndereco(int idAtt, Endereco endereco) throws SQLException {
        String query = "UPDATE Endereco SET LOGRADOURO = ?, NUMERO = ?, "
                + "COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, UF = ?, CEP = ? "
                + "WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, endereco.getLogradouro());
            ps.setString(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getUf());
            ps.setString(7, endereco.getCep());
            ps.setInt(8, idAtt);
        
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao atuaizar Endereco no banco de dados!");
        }
    }
    
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
}
