/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package top.dribles.atividade.web.interfaces;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;
import top.dribles.atividade.web.model.Pessoa;
import top.dribles.atividade.web.model.Endereco;
import top.dribles.atividade.web.model.Especialidade;
import top.dribles.atividade.web.model.Medico;
import top.dribles.atividade.web.model.Paciente;

/**
 *
 * @author crist
 */
@WebService
public interface WebServiceClinica {
//  ----------------------------  Pessoa  -------------------------------------
    
    @WebMethod
    Pessoa getPessoaById(int id) throws SQLException;
    
    @WebMethod
    Pessoa adicionarPessoa(Pessoa pessoa) throws SQLException;
    
    @WebMethod
    void atualizarPessoa(Pessoa pessoa, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) throws SQLException;
    
//  ----------------------------  Medico  -------------------------------------
    
    @WebMethod
    public ArrayList<Medico> getAllMedicos() throws SQLException;
    
    @WebMethod
    public Medico getMedicoById(int id) throws SQLException;
    
    @WebMethod
    public Medico adicionarMedico(int pessoa_id, int especialidade_id, String crm) throws SQLException;
    
//    @WebMethod
//    public void atualizarMedico(Medico medico, String nome, String telefone, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) throws SQLException;
    
    @WebMethod
    public void deletarMedico(int id) throws SQLException;
    
    //  ----------------------------  Paciente  -------------------------------------
    
    @WebMethod
    public ArrayList<Paciente> getAllPacientes() throws SQLException;
    
    @WebMethod
    public Paciente getPacienteById(int id) throws SQLException;
    
    @WebMethod
    public Paciente adicionarPaciente(int pessoa_id) throws SQLException;
    
//    @WebMethod
//    public void atualizarPaciente(Paciente paciente) throws SQLException;
    
    @WebMethod
    public void deletarPaciente(int id) throws SQLException;

//  ---------------------------  Endereco  ------------------------------------
    
    @WebMethod
    ArrayList<Endereco> getAllEnderecos() throws SQLException;
    
    @WebMethod
    Endereco getEnderecoById(int id) throws SQLException;
    
    @WebMethod
    Endereco adicionarEndereco(Endereco endereco) throws SQLException;
    
    @WebMethod
    void atualizarEndereco(Endereco endereco) throws SQLException;
    
//  --------------------------  Especialidade  --------------------------------
    
    @WebMethod
    ArrayList<Especialidade> getAllEspecialidades() throws SQLException;
    
    @WebMethod
    Especialidade getEspecialidadeById(int id) throws SQLException;
    
    @WebMethod
    Especialidade adicionarEspecialidade(Especialidade especialidade) throws SQLException;
    
    @WebMethod
    void deletarEspecialidade(int id) throws SQLException;
}
