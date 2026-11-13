package com.empresa.reembolsos.aplicacion;

import com.empresa.reembolsos.dominio.*;
import com.empresa.reembolsos.infraestructura.RepositorioSolicitudesJpa;
import com.empresa.reembolsos.procesamiento.calculo.SelectorCalculadora;
import com.empresa.reembolsos.procesamiento.notificacion.ObservadorCambioEstado;
import com.empresa.reembolsos.procesamiento.validacion.CadenaValidadores;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudReembolsoService {
    private final CadenaValidadores validadores;
    private final SelectorCalculadora calculadoras;
    private final List<ObservadorCambioEstado> observadores;
    private final RepositorioSolicitudesJpa repositorio;

    public SolicitudReembolsoService(
            CadenaValidadores validadores,
            SelectorCalculadora calculadoras,
            List<ObservadorCambioEstado> observadores,
            RepositorioSolicitudesJpa repositorio) {
        this.validadores = validadores;
        this.calculadoras = calculadoras;
        this.observadores = observadores;
        this.repositorio = repositorio;
    }

    public SolicitudReembolso crear(SolicitudReembolso solicitud) {
        validarEntrada(solicitud);
        var pendientes =
                repositorio.findByEmpleadoIdAndEstado(
                        solicitud.getEmpleadoId(), EstadoSolicitud.PENDIENTE);
        var resultado = validadores.validar(solicitud, pendientes);
        if (!resultado.valida()) {
            throw new SolicitudInvalidaException(resultado.motivoRechazo());
        }
        solicitud.setId(null);
        solicitud.setMontoAprobado(
                calculadoras.obtener(solicitud.getCategoria()).calcular(solicitud));
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        return repositorio.save(solicitud);
    }

    public SolicitudReembolso cambiarEstado(Long id, EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) {
            throw new SolicitudInvalidaException("El nuevo estado es obligatorio");
        }
        var solicitud =
                repositorio
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Solicitud no encontrada: " + id));
        var estadoAnterior = solicitud.getEstado();
        solicitud.setEstado(nuevoEstado);
        var guardada = repositorio.save(solicitud);
        observadores.forEach(o -> o.onCambioEstado(guardada, estadoAnterior));
        return guardada;
    }

    private void validarEntrada(SolicitudReembolso s) {
        if (s.getEmpleadoId() == null
                || s.getEmpleadoId().isBlank()
                || s.getCategoria() == null
                || s.getFechaGasto() == null
                || s.getMontoSolicitado() == null) {
            throw new SolicitudInvalidaException(
                    "Empleado, categoria, fecha y monto son obligatorios");
        }
        if (s.getMontoSolicitado().signum() <= 0
                || (s.getCategoria() == CategoriaGasto.VIAJE && s.getDiasViaje() <= 0)) {
            throw new SolicitudInvalidaException(
                    "El monto y los dias de viaje deben ser positivos");
        }
    }
}
