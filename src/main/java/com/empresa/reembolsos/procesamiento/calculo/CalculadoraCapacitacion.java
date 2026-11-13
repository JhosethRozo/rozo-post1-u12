package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraCapacitacion implements CalculadoraReembolso {
    @Override
    public CategoriaGasto getCategoria() {
        return CategoriaGasto.CAPACITACION;
    }

    @Override
    public BigDecimal calcular(SolicitudReembolso solicitud) {
        return solicitud
                .getMontoSolicitado()
                .multiply(new BigDecimal("0.8"))
                .min(new BigDecimal("3000000"));
    }
}
