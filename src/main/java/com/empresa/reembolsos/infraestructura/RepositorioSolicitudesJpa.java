package com.empresa.reembolsos.infraestructura;

import com.empresa.reembolsos.dominio.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioSolicitudesJpa extends JpaRepository<SolicitudReembolso, Long> {
    List<SolicitudReembolso> findByEmpleadoIdAndEstado(String empleadoId, EstadoSolicitud estado);
}
