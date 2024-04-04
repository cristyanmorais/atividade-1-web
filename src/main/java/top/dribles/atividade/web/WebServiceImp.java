/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web;

import jakarta.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.interfaces.WebServiceClinica;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Especialidade;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.model.Paciente;
import top.dribles.atividade.web.services.EnderecoService;
import top.dribles.atividade.web.services.EspecialidadeService;
import top.dribles.atividade.web.services.MedicoService;
import top.dribles.atividade.web.services.PacienteService;
import top.dribles.atividade.web.services.PessoaService;

/**
 *
 * @author crist
 */
@WebService(endpointInterface = "top.dribles.atividade.web.interfaces.WebServiceClinica")
public class WebServiceImp implements WebServiceClinica{
    
    private final EnderecoService enderecoService;
    private final EspecialidadeService especialidadeService;
    private final PessoaService pessoaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    
    public WebServiceImp() throws SQLException {
        this.enderecoService = new EnderecoService();
        this.especialidadeService = new EspecialidadeService();
        this.pessoaService = new PessoaService();
        this.medicoService = new MedicoService();
        this.pacienteService = new PacienteService();
    }
    
//  ------------------------------  Pessoa  -----------------------------------
   
    @Override
    public Pessoa getPessoaById(int id) throws SQLException {
        return pessoaService.getPessoaById(id);
    }
    
    @Override
    public Pessoa adicionarPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
        return pessoaService.adicionarPessoa(pessoa, endereco);
    }
    
    @Override
    public void atualizarPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
        pessoaService.atualizarPessoa(pessoa, endereco);
    }
    
//  ------------------------------  Medico  -----------------------------------
    
    @Override
    public ArrayList<Medico> getAllMedicos() throws SQLException {
        return medicoService.getAllMedicos();
    }
    
    @Override
    public Medico getMedicoById(int id) throws SQLException {
        return medicoService.getMedicoById(id);
    }
    
    @Override
    public Medico adicionarMedico(Medico medico, Pessoa pessoa, Endereco endereco) throws SQLException {
        return medicoService.adicionarMedico(medico, pessoa, endereco);
    }
    
    @Override
    public void atualizarMedico(Medico medico, Pessoa pessoa, Endereco endereco) throws SQLException {
        medicoService.atualizarMedico(medico, pessoa, endereco);
    }
    
    @Override
    public void deletarMedico(int id) throws SQLException {
        medicoService.deletarMedico(id);
    }
    
    //  ------------------------------  Paciente  -----------------------------------
    
    @Override
    public ArrayList<Paciente> getAllPacientes() throws SQLException {
        return pacienteService.getAllPacientes();
    }
    
    @Override
    public Paciente getPacienteById(int id) throws SQLException {
        return pacienteService.getPacienteById(id);
    }
    
    @Override
    public Paciente adicionarPaciente(Paciente paciente, Pessoa pessoa, Endereco endereco) throws SQLException {
        return pacienteService.adicionarPaciente(paciente, pessoa, endereco);
    }
    
    @Override
    public void atualizarPaciente(Paciente paciente, Pessoa pessoa, Endereco endereco) throws SQLException {
        pacienteService.atualizarPaciente(paciente, pessoa, endereco);
    }
    
    @Override
    public void deletarPaciente(int id) throws SQLException {
        pacienteService.deletarPaciente(id);
    }
    
//  -----------------------------  Endereco  ----------------------------------
    
    @Override
    public ArrayList<Endereco> getAllEnderecos() throws SQLException {
        return enderecoService.getAllEnderecos();
    }
    
    @Override
    public Endereco getEnderecoById(int id) throws SQLException {
        return enderecoService.getEnderecoById(id);
    }
    
    @Override
    public Endereco adicionarEndereco(Endereco endereco) throws SQLException {
        return enderecoService.adicionarEndereco(endereco.getLogradouro(), endereco.getNumero(), 
                endereco.getComplemento(), endereco.getBairro(), endereco.getCidade(), 
                endereco.getUf(), endereco.getCep());
    }
    
    @Override
    public void atualizarEndereco(Endereco endereco) throws SQLException {
        enderecoService.atualizarEndereco(endereco);
    }
    
//  --------------------------  Especialidade  --------------------------------
    
    @Override
    public ArrayList<Especialidade> getAllEspecialidades() throws SQLException {
        return especialidadeService.getAllEspecialidades();
    }
    
    @Override
    public Especialidade getEspecialidadeById(int id) throws SQLException {
        return especialidadeService.getEspecialidadeById(id);
    }
    
    @Override
    public Especialidade adicionarEspecialidade(Especialidade especialidade) throws SQLException {
        return especialidadeService.adicionarEspecialidade(especialidade.getNome());
    }
    
    @Override
    public void deletarEspecialidade(int id) throws SQLException {
        especialidadeService.deletarEspecialidade(id);
    }
}
