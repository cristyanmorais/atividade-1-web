/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.repositories.EnderecoRepository;

/**
 *
 * @author crist
 */
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    
    public EnderecoService() throws SQLException {
        this.enderecoRepository = new EnderecoRepository();
    }
    
    public ArrayList<Endereco> getAllEnderecos() throws SQLException {
        return enderecoRepository.getAllEnderecos();
    }
    
    public Endereco getEnderecoById(int id) throws SQLException {
        return enderecoRepository.getEnderecoById(id);
    }
    
    public Endereco adicionarEndereco(String logradouro, String numero, String complemento, 
            String bairro, String cidade, String uf, String cep) throws SQLException {
        
        Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, uf, cep);
        return enderecoRepository.adicionarEndereco(endereco);
    }
    
    public void atualizarEndereco(Endereco endereco) throws SQLException {
        enderecoRepository.atualizarEndereco(endereco);
    }
}