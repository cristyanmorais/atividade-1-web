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
    
     public Pessoa adicionarPessoa(Pessoa pessoa) throws SQLException {
        return pessoaRepository.adicionarPessoa(pessoa);
    }
    
    public void atualizarPessoa(int idAtt, Pessoa pessoa) throws SQLException {
        pessoaRepository.atualizarPessoa(idAtt, pessoa);
    }
}
