/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package top.dribles.atividade.web.services;

import java.sql.SQLException;
import top.dribles.atividade.web.model.Consulta;
import top.dribles.atividade.web.repositories.ConsultaRepository;

/**
 *
 * @author crist
 */
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    
    public ConsultaService() throws SQLException {
        this.consultaRepository = new ConsultaRepository();
    }
    
    public Consulta adicionarConsulta(Consulta consulta) throws SQLException {
        return consultaRepository.adicionarConsulta(consulta);
    }
    
    public void cancelarConsulta(int idCancelamento, int idConsulta) throws SQLException {
        consultaRepository.cancelarConsulta(idConsulta, idCancelamento);
    }
}
