/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Paciente;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.repositories.PacienteRepository;

/**
 *
 * @author crist
 */
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    
    public PacienteService() throws SQLException {
        this.pacienteRepository = new PacienteRepository();
    }
    
    public ArrayList<Paciente> getAllPacientes() throws SQLException {
        return pacienteRepository.getAllPacientes();
    }
    
    public Paciente getPacienteById(int id) throws SQLException {
        return pacienteRepository.getPacienteById(id);
    }
    
    public Paciente adicionarPaciente(Paciente paciente, Pessoa pessoa, Endereco endereco) throws SQLException {
        return pacienteRepository.adicionarPaciente(paciente, pessoa, endereco);
    }
    
    public void atualizarPaciente(Paciente paciente, Pessoa pessoa, Endereco endereco) throws SQLException {
        pacienteRepository.atualizarPaciente(paciente, pessoa, endereco);
    }
    
    public void deletarPaciente(int id) throws SQLException {
        pacienteRepository.deletarPaciente(id);
    }
}
