package com.empresa.reembolsos.procesamiento.validacion;

import static org.junit.jupiter.api.Assertions.*;

import com.empresa.reembolsos.Datos;
import com.empresa.reembolsos.dominio.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class ValidadorDocumentosSoporteTest {
    @Test
    void validar_sinDocumentos_rechazaConMotivo() {
        var s = Datos.solicitud(CategoriaGasto.VIAJE, "100000");
        s.setDocumentosSoporte(false);
        var r = new ValidadorDocumentosSoporte().validar(s, List.of());
        assertFalse(r.valida());
        assertEquals("Faltan documentos soporte", r.motivoRechazo());
    }

    @Test
    void validar_conDocumentos_acepta() {
        assertTrue(
                new ValidadorDocumentosSoporte()
                        .validar(Datos.solicitud(CategoriaGasto.VIAJE, "100000"), List.of())
                        .valida());
    }
}
