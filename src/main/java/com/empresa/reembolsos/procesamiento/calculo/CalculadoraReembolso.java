package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;

import java.math.BigDecimal;

/** Estrategia de cálculo específica de una categoría de gasto. */
public interface CalculadoraReembolso {
    CategoriaGasto getCategoria();

    BigDecimal calcular(SolicitudReembolso solicitud);
}
