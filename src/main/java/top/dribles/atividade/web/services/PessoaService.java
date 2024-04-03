/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.repositories.PessoaRepository;

/**
 *
 * @author crist
 */
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    
    public PessoaService() throws SQLException {
        this.pessoaRepository = new PessoaRepository();
    }
    
    public Pessoa getPessoaById(int id) throws SQLException {
        return pessoaRepository.getPessoaById(id);
    }
    
     public Pessoa adicionarPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
        return pessoaRepository.adicionarPessoa(pessoa, endereco);
    }
    
    public void atualizarPessoa(Pessoa pessoa, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) throws SQLException {
        pessoaRepository.atualizarPessoa(pessoa, logradouro, numero, complemento, bairro, cidade, uf, cep);
    }
}
