package com.empresa.reembolsos.procesamiento.calculo;
import com.empresa.reembolsos.Datos;
import com.empresa.reembolsos.dominio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;
class CalculadoraRepresentacionTest {
 @ParameterizedTest @CsvSource({"100000,100000","2500000,2000000"})
 void calcular_montoBajoYSobreTope_respetaFormula(String monto,String esperado) {
  var estrategia=new CalculadoraRepresentacion(); assertEquals(CategoriaGasto.REPRESENTACION,estrategia.getCategoria());
  assertEquals(0, new BigDecimal(esperado).compareTo(estrategia.calcular(Datos.solicitud(CategoriaGasto.REPRESENTACION,monto))));
 }
}
