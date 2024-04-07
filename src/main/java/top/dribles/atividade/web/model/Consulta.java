/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private Date data;
    private String horario;
    private boolean is_active;

    public Consulta() {
        
    }

    public Consulta(int medico_id, int paciente_id, int motivo_cancelamento_id, Date data, String horario, boolean is_active) {
        this.medico_id = medico_id;
        this.paciente_id = paciente_id;
        this.motivo_cancelamento_id = motivo_cancelamento_id;
        this.data = data;
        this.horario = horario;
        this.is_active = is_active;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
