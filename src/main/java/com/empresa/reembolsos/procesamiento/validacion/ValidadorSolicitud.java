package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.*;

import java.util.List;

/** Eslabón que detiene la validación al fallar o delega en el siguiente. */
public abstract class ValidadorSolicitud {
    private ValidadorSolicitud siguiente;

    public ValidadorSolicitud siguiente(ValidadorSolicitud siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public ResultadoValidacion validar(
            SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes) {
        ResultadoValidacion propio = validarPropio(solicitud, pendientes);
        if (!propio.valida() || siguiente == null) {
            return propio;
        }
        return siguiente.validar(solicitud, pendientes);
    }

    protected abstract ResultadoValidacion validarPropio(
            SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes);
}
