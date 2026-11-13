package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraViaje implements CalculadoraReembolso {
    @Override
    public CategoriaGasto getCategoria() {
        return CategoriaGasto.VIAJE;
    }

    @Override
    public BigDecimal calcular(SolicitudReembolso solicitud) {
        return solicitud
                .getMontoSolicitado()
                .min(
                        new BigDecimal("150000")
                                .multiply(BigDecimal.valueOf(solicitud.getDiasViaje())));
    }
}
