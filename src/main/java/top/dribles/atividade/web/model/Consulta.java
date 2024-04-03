/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.model;

import java.util.Date;

/**
 *
 * @author crist
 */
public class Consulta {
    private int id;
    private int medico_id;
    private int paciente_id;
    private int motivo_cancelamento_id;
    private Date data_hora;

    public Consulta() {
        
    }

    public Consulta(int medico_id, int paciente_id, int motivo_cancelamento_id, Date data_hora) {
        this.medico_id = medico_id;
        this.paciente_id = paciente_id;
        this.motivo_cancelamento_id = motivo_cancelamento_id;
        this.data_hora = data_hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedico_id() {
        return medico_id;
    }

    public void setMedico_id(int medico_id) {
        this.medico_id = medico_id;
    }

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int paciente_id) {
        this.paciente_id = paciente_id;
    }

    public int getMotivo_cancelamento_id() {
        return motivo_cancelamento_id;
    }

    public void setMotivo_cancelamento_id(int motivo_cancelamento_id) {
        this.motivo_cancelamento_id = motivo_cancelamento_id;
    }

    public Date getData_hora() {
        return data_hora;
    }

    public void setData_hora(Date data_hora) {
        this.data_hora = data_hora;
    }
}
