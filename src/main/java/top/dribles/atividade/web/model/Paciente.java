/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.model;

/**
 *
 * @author crist
 */
public class Paciente {
    private int id;
    private Pessoa pessoa;
    private boolean is_active;
    
    private String lista_nome;
    private String lista_email;
    private String lista_cpf;

    public Paciente() {
        
    }

    public Paciente(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Paciente(Pessoa pessoa, boolean is_active) {
        this.pessoa = pessoa;
        this.is_active = is_active;
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

    public String getLista_cpf() {
        return lista_cpf;
    }

    public void setLista_cpf(String lista_cpf) {
        this.lista_cpf = lista_cpf;
    }
}
