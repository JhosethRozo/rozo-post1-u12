package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.*;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class ValidadorMontoMaximo extends ValidadorSolicitud {
    private static final java.util.Map<CategoriaGasto, java.math.BigDecimal> TOPES = java.util.Map.of(
        CategoriaGasto.VIAJE, new java.math.BigDecimal("150000"),
        CategoriaGasto.REPRESENTACION, new java.math.BigDecimal("2000000"),
        CategoriaGasto.CAPACITACION, new java.math.BigDecimal("3000000"));
    @Override
    protected ResultadoValidacion validarPropio(SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes) {
        var tope = TOPES.get(solicitud.getCategoria());
        return solicitud.getMontoSolicitado().compareTo(tope) > 0
            ? ResultadoValidacion.rechazar("Monto excede el tope de la categoria " + solicitud.getCategoria())
            : ResultadoValidacion.ok();
    }
}
