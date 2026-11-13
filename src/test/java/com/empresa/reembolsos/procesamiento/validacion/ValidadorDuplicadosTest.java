package com.empresa.reembolsos.procesamiento.validacion;

import static org.junit.jupiter.api.Assertions.*;

import com.empresa.reembolsos.Datos;
import com.empresa.reembolsos.dominio.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class ValidadorDuplicadosTest {
    @Test
    void validar_mismaCategoriaYPeriodo_rechaza() {
        var s = Datos.solicitud(CategoriaGasto.VIAJE, "100000");
        assertFalse(new ValidadorDuplicados().validar(s, List.of(s)).valida());
    }

    @Test
    void validar_mismoMesOtroAnio_acepta() {
        var s = Datos.solicitud(CategoriaGasto.VIAJE, "100000");
        var p = Datos.solicitud(CategoriaGasto.VIAJE, "100000");
        p.setFechaGasto(p.getFechaGasto().minusYears(1));
        assertTrue(new ValidadorDuplicados().validar(s, List.of(p)).valida());
    }

    @Test
    void validar_otraCategoria_acepta() {
        assertTrue(
                new ValidadorDuplicados()
                        .validar(
                                Datos.solicitud(CategoriaGasto.VIAJE, "100000"),
                                List.of(Datos.solicitud(CategoriaGasto.CAPACITACION, "100000")))
                        .valida());
    }
}
