package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
@Component
public class CalculadoraCapacitacion implements CalculadoraReembolso {
    @Override public CategoriaGasto getCategoria() { return CategoriaGasto.CAPACITACION; }
    @Override public BigDecimal calcular(SolicitudReembolso solicitud) { return solicitud.getMontoSolicitado().multiply(new BigDecimal("0.8")).min(new BigDecimal("3000000")); }
}
