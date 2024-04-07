/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.model;

/**
 *
 * @author crist
 */
public class Medico {
    private int id;
    private Pessoa pessoa;
    private Especialidade especialidade;
    private String crm;
    private boolean is_active;
    
    private String lista_nome;
    private String lista_email;
    private String lista_especialidade;

    public Medico() {
        
    }

    public Medico(Pessoa pessoa, Especialidade especialidade, String crm) {
        this.pessoa = pessoa;
        this.especialidade = especialidade;
        this.crm = crm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getLista_nome() {
        return lista_nome;
    }

    public void setLista_nome(String lista_nome) {
        this.lista_nome = lista_nome;
    }

    public String getLista_email() {
        return lista_email;
    }

    public void setLista_email(String lista_email) {
        this.lista_email = lista_email;
    }

    public String getLista_especialidade() {
        return lista_especialidade;
    }

    public void setLista_especialidade(String lista_especialidade) {
        this.lista_especialidade = lista_especialidade;
    }
}
