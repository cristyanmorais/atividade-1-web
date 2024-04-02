/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Especialidade;
import top.dribles.atividade.web.repositories.EspecialidadeRepository;

/**
 *
 * @author crist
 */
public class EspecialidadeService {
    
    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService() throws SQLException {
        this.especialidadeRepository = new EspecialidadeRepository();
    }
    
    public ArrayList<Especialidade> getAllEspecialidades() throws SQLException {
        return especialidadeRepository.getAllEspecialidades();
    }
    
    public Especialidade getEspecialidadeById(int id) throws SQLException {
        return especialidadeRepository.getEspecialidadeById(id);
    }
    
    public Especialidade adicionarEspecialidade(String nome) throws SQLException {
        Especialidade especialidade = new Especialidade(nome);
        return especialidadeRepository.adicionarEspecialidade(especialidade);
    }
    
    public void deletarEspecialidade(int id) throws SQLException {
        especialidadeRepository.deletarEspecialidade(id);
    }
}
