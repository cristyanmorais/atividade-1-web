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
import top.dribles.atividade.web.model.Especialidade;

/**
 *
 * @author crist
 */
public class EspecialidadeRepository {
    
    private final Connection conn;
    
    public EspecialidadeRepository() throws SQLException {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    public ArrayList<Especialidade> getAllEspecialidades() throws SQLException {
        ArrayList<Especialidade> especialidades = new ArrayList<>();
        String query = "SELECT * FROM Especialidade";
    
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("ID"));
                especialidade.setNome(rs.getString("NOME"));
                especialidades.add(especialidade);
            }
        }
    
        return especialidades;
    }
    
    public Especialidade getEspecialidadeById(int id) throws SQLException {
        String query = "SELECT * FROM Especialidade WHERE ID = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Especialidade especialidade = new Especialidade();
                    especialidade.setId(rs.getInt("ID"));
                    especialidade.setNome(rs.getString("NOME"));
                    return especialidade;
                }
            }
        }
    
        return null;
    }
    
    public Especialidade adicionarEspecialidade(Especialidade especialidade) throws SQLException {
        String query = 
                "INSERT INTO Especialidade (NOME) "
                + "VALUES(?);";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
                
        try {
            ps = conn.prepareStatement(query, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, especialidade.getNome());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            especialidade.setId(rs.getInt(1));
            
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        }
        
        return especialidade;
    }
    
    public void deletarEspecialidade(int id) throws SQLException {
        String query = "DELETE FROM Especialidade WHERE ID = ?";

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
