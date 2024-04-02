/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.repositories.MedicoRepository;

/**
 *
 * @author crist
 */
public class MedicoService {
    private final MedicoRepository medicoRepository;
    
    public MedicoService() throws SQLException {
        this.medicoRepository = new MedicoRepository();
    }
    
    public ArrayList<Medico> getAllMedicos() throws SQLException {
        return medicoRepository.getAllMedicos();
    }
    
    public Medico getMedicoById(int id) throws SQLException {
        return medicoRepository.getMedicoById(id);
    }
    
    public Medico adicionarMedico(int pessoa_id, int especialidade_id, String crm) throws SQLException {
        
        Medico medico = new Medico(pessoa_id, especialidade_id, crm);
        return medicoRepository.adicionarMedico(medico);
    }
    
//    public void atualizarMedico(Medico medico, String nome, String telefone, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) throws SQLException {
//        medicoRepository.atualizarMedico(medico, nome, telefone, logradouro, numero, complemento, bairro, cidade, uf, cep);
//    }
    
    public void deletarMedico(int id) throws SQLException {
        medicoRepository.deletarMedico(id);
    }
}