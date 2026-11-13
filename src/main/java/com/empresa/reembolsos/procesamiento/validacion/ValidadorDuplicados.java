package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.*;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidadorDuplicados extends ValidadorSolicitud {
    @Override
    protected ResultadoValidacion validarPropio(
            SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes) {
        boolean existe =
                pendientes.stream()
                        .anyMatch(
                                p ->
                                        p.getCategoria() == solicitud.getCategoria()
                                                && java.time.YearMonth.from(p.getFechaGasto())
                                                        .equals(
                                                                java.time.YearMonth.from(
                                                                        solicitud
                                                                                .getFechaGasto())));
        return existe
                ? ResultadoValidacion.rechazar(
                        "Ya existe una solicitud pendiente de la misma categoria en el periodo")
                : ResultadoValidacion.ok();
    }
}
