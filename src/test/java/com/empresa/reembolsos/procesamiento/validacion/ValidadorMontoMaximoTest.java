package com.empresa.reembolsos.procesamiento.validacion;
import com.empresa.reembolsos.Datos;
import com.empresa.reembolsos.dominio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class ValidadorMontoMaximoTest {
@Test void validar_montoEnTope_acepta() { assertTrue(new ValidadorMontoMaximo().validar(Datos.solicitud(CategoriaGasto.VIAJE,"150000"),List.of()).valida()); }
@Test void validar_montoSuperior_rechaza() { assertFalse(new ValidadorMontoMaximo().validar(Datos.solicitud(CategoriaGasto.VIAJE,"150000.01"),List.of()).valida()); }
}
