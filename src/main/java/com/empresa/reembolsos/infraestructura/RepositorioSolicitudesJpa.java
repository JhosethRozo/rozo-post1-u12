package com.empresa.reembolsos.infraestructura;

import com.empresa.reembolsos.dominio.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioSolicitudesJpa extends JpaRepository<SolicitudReembolso, Long> {
    List<SolicitudReembolso> findByEmpleadoIdAndEstado(String empleadoId, EstadoSolicitud estado);
}
