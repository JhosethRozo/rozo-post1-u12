package com.empresa.reembolsos.adaptadores.rest;

import com.empresa.reembolsos.aplicacion.*;
import com.empresa.reembolsos.dominio.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudReembolsoController {
    private final SolicitudReembolsoService servicio;
    public SolicitudReembolsoController(SolicitudReembolsoService servicio) { this.servicio = servicio; }

    @PostMapping
    public ResponseEntity<SolicitudReembolso> crear(@RequestBody SolicitudReembolso solicitud) {
        return ResponseEntity.ok(servicio.crear(solicitud));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudReembolso> cambiarEstado(@PathVariable Long id, @RequestBody EstadoSolicitud nuevoEstado) {
        return ResponseEntity.ok(servicio.cambiarEstado(id, nuevoEstado));
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<String> solicitudInvalida(SolicitudInvalidaException error) {
        return ResponseEntity.badRequest().body(error.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> noEncontrada(IllegalArgumentException error) {
        return ResponseEntity.notFound().build();
    }
}
