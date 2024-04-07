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
import top.dribles.atividade.web.model.Consulta;
import top.dribles.atividade.web.services.EnderecoService;
import top.dribles.atividade.web.services.EspecialidadeService;
import top.dribles.atividade.web.services.MedicoService;
import top.dribles.atividade.web.services.PacienteService;
import top.dribles.atividade.web.services.PessoaService;
import top.dribles.atividade.web.services.ConsultaService;

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
    private final ConsultaService consultaService;
    
    public WebServiceImp() throws SQLException {
        this.enderecoService = new EnderecoService();
        this.especialidadeService = new EspecialidadeService();
        this.pessoaService = new PessoaService();
        this.medicoService = new MedicoService();
        this.pacienteService = new PacienteService();
        this.consultaService = new ConsultaService();
    }
    
//  ----------------------------  Consulta  -----------------------------------
    
    @Override
    public Consulta adicionarConsulta(Consulta consulta) throws SQLException {
        return consultaService.adicionarConsulta(consulta);
    }
    
    @Override
    public void cancelarConsulta(int idCancelamento, int idConsulta) throws SQLException {
        consultaService.cancelarConsulta(idConsulta, idCancelamento);
    }
    
//  ------------------------------  Pessoa  -----------------------------------
   
    @Override
    public Pessoa getPessoaById(int id) throws SQLException {
        return pessoaService.getPessoaById(id);
    }
    
    @Override
    public Pessoa adicionarPessoa(Pessoa pessoa) throws SQLException {
        return pessoaService.adicionarPessoa(pessoa);
    }
    
    @Override
    public void atualizarPessoa(int idAtt, Pessoa pessoa) throws SQLException {
        pessoaService.atualizarPessoa(idAtt, pessoa);
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
    public Medico adicionarMedico(Medico medico) throws SQLException {
        return medicoService.adicionarMedico(medico);
    }
    
    @Override
    public void atualizarMedico(int idAtt, Medico medico) throws SQLException {
        medicoService.atualizarMedico(idAtt, medico);
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
    public Paciente adicionarPaciente(Paciente paciente) throws SQLException {
        return pacienteService.adicionarPaciente(paciente);
    }
    
    @Override
    public void atualizarPaciente(int idAtt, Paciente paciente) throws SQLException {
        pacienteService.atualizarPaciente(idAtt, paciente);
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
        return enderecoService.adicionarEndereco(endereco);
    }
    
    @Override
    public void atualizarEndereco(int idAtt, Endereco endereco) throws SQLException {
        enderecoService.atualizarEndereco(idAtt, endereco);
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
