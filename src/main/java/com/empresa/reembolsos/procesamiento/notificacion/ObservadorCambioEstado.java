package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;

/** Suscriptor síncrono de todo cambio de estado persistido por el servicio. */
public interface ObservadorCambioEstado {
    void onCambioEstado(SolicitudReembolso solicitud, EstadoSolicitud estadoAnterior);
}
