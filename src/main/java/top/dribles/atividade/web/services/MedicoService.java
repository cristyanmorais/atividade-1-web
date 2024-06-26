/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;
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
    
    public Medico adicionarMedico(Medico medico) throws SQLException {
        return medicoRepository.adicionarMedico(medico);
    }
    
    public void atualizarMedico(int idAtt, Medico medico) throws SQLException {
        medicoRepository.atualizarMedico(idAtt, medico);
    }
    
    public void deletarMedico(int id) throws SQLException {
        medicoRepository.deletarMedico(id);
    }
}
