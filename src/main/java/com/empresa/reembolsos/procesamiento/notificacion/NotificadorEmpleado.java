package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;
import org.springframework.stereotype.Component;
@Component
public class NotificadorEmpleado implements ObservadorCambioEstado {
    @Override public void onCambioEstado(SolicitudReembolso solicitud, EstadoSolicitud estadoAnterior) {
        System.out.println("Notificando a " + solicitud.getEmpleadoId() + ": su solicitud paso de " + estadoAnterior + " a " + solicitud.getEstado());
    }
}
