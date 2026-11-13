package com.empresa.reembolsos;

import com.empresa.reembolsos.dominio.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Datos {
    private Datos() {}

    public static SolicitudReembolso solicitud(CategoriaGasto categoria, String monto) {
        var s = new SolicitudReembolso();
        s.setEmpleadoId("E-1");
        s.setCategoria(categoria);
        s.setMontoSolicitado(new BigDecimal(monto));
        s.setFechaGasto(LocalDate.of(2026, 8, 10));
        s.setDiasViaje(2);
        s.setDocumentosSoporte(true);
        s.setEstado(EstadoSolicitud.PENDIENTE);
        return s;
    }
}
