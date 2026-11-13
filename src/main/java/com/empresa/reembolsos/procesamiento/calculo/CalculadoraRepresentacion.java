package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
@Component
public class CalculadoraRepresentacion implements CalculadoraReembolso {
    @Override public CategoriaGasto getCategoria() { return CategoriaGasto.REPRESENTACION; }
    @Override public BigDecimal calcular(SolicitudReembolso solicitud) { return solicitud.getMontoSolicitado().min(new BigDecimal("2000000")); }
}
