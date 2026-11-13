package com.empresa.reembolsos.procesamiento.calculo;

import static org.junit.jupiter.api.Assertions.*;

import com.empresa.reembolsos.Datos;
import com.empresa.reembolsos.dominio.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

class CalculadoraCapacitacionTest {
    @ParameterizedTest
    @CsvSource({"1000000,800000", "5000000,3000000"})
    void calcular_montoBajoYSobreTope_respetaFormula(String monto, String esperado) {
        var estrategia = new CalculadoraCapacitacion();
        assertEquals(CategoriaGasto.CAPACITACION, estrategia.getCategoria());
        assertEquals(
                0,
                new BigDecimal(esperado)
                        .compareTo(
                                estrategia.calcular(
                                        Datos.solicitud(CategoriaGasto.CAPACITACION, monto))));
    }
}
