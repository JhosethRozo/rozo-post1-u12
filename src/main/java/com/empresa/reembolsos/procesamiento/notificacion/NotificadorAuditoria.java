package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;

import org.springframework.stereotype.Component;

@Component
public class NotificadorAuditoria implements ObservadorCambioEstado {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(NotificadorAuditoria.class);

    @Override
    public void onCambioEstado(SolicitudReembolso solicitud, EstadoSolicitud estadoAnterior) {
        log.info(
                "Auditoria: solicitud {} cambio de {} a {}",
                solicitud.getId(),
                estadoAnterior,
                solicitud.getEstado());
    }
}
