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
    private int pessoa_id;
    private int especialidade_id;
    private String crm;
    private boolean is_active;

    public Medico() {
        
    }

    public Medico(int pessoa_id, int especialidade_id, String crm) {
        this.pessoa_id = pessoa_id;
        this.especialidade_id = especialidade_id;
        this.crm = crm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPessoa_id() {
        return pessoa_id;
    }

    public void setPessoa_id(int pessoa_id) {
        this.pessoa_id = pessoa_id;
    }

    public int getEspecialidade_id() {
        return especialidade_id;
    }

    public void setEspecialidade_id(int especialidade_id) {
        this.especialidade_id = especialidade_id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
