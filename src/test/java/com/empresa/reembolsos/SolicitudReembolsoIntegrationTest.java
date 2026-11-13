package com.empresa.reembolsos;
import com.empresa.reembolsos.dominio.*;
import com.empresa.reembolsos.infraestructura.RepositorioSolicitudesJpa;
import com.empresa.reembolsos.procesamiento.notificacion.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.system.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@SpringBootTest @AutoConfigureMockMvc @ExtendWith(OutputCaptureExtension.class)
class SolicitudReembolsoIntegrationTest {
 @Autowired MockMvc mvc;
 @Autowired ObjectMapper json;
 @Autowired RepositorioSolicitudesJpa repositorio;
 @SpyBean NotificadorEmpleado empleado;
 @SpyBean NotificadorContabilidad contabilidad;
 @SpyBean NotificadorAuditoria auditoria;
 @BeforeEach void limpiar() { repositorio.deleteAll(); }
 @ParameterizedTest @EnumSource(CategoriaGasto.class)
 void crear_tresCategorias_calculaYPersiste(CategoriaGasto categoria) throws Exception {
  var s=Datos.solicitud(categoria,"100000");
  var respuesta=mvc.perform(post("/api/solicitudes").contentType("application/json").content(json.writeValueAsString(s)))
   .andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("PENDIENTE"))
   .andExpect(jsonPath("$.montoAprobado").value(categoria==CategoriaGasto.CAPACITACION?80000:100000)).andReturn();
  var guardada=json.readValue(respuesta.getResponse().getContentAsString(),SolicitudReembolso.class);
  assertTrue(repositorio.findById(guardada.getId()).isPresent());
 }
 @Test void crear_sinDocumentos_devuelveMensajeClaro() throws Exception {
  var s=Datos.solicitud(CategoriaGasto.VIAJE,"100000"); s.setDocumentosSoporte(false);
  mvc.perform(post("/api/solicitudes").contentType("application/json").content(json.writeValueAsString(s)))
   .andExpect(status().isBadRequest()).andExpect(content().string("Faltan documentos soporte"));
  assertEquals(0,repositorio.count());
 }
 @Test void crear_duplicadoPendiente_rechaza() throws Exception {
  repositorio.save(Datos.solicitud(CategoriaGasto.VIAJE,"100000"));
  mvc.perform(post("/api/solicitudes").contentType("application/json").content(json.writeValueAsString(Datos.solicitud(CategoriaGasto.VIAJE,"100000"))))
   .andExpect(status().isBadRequest()).andExpect(content().string("Ya existe una solicitud pendiente de la misma categoria en el periodo"));
 }
 @ParameterizedTest @EnumSource(value=EstadoSolicitud.class,names={"APROBADA","RECHAZADA","REQUIERE_CORRECCION"})
 void cambiarEstado_tresResultados_notificaATodosYContabilidadSoloPagaAprobaciones(EstadoSolicitud nuevo, CapturedOutput salida) throws Exception {
  var s=repositorio.save(Datos.solicitud(CategoriaGasto.VIAJE,"100000"));
  s.setMontoAprobado(new java.math.BigDecimal("100000")); repositorio.save(s);
  mvc.perform(put("/api/solicitudes/"+s.getId()+"/estado").contentType("application/json").content(json.writeValueAsString(nuevo)))
   .andExpect(status().isOk()).andExpect(jsonPath("$.estado").value(nuevo.name()));
  verify(empleado).onCambioEstado(any(),eq(EstadoSolicitud.PENDIENTE));
  verify(contabilidad).onCambioEstado(any(),eq(EstadoSolicitud.PENDIENTE));
  verify(auditoria).onCambioEstado(any(),eq(EstadoSolicitud.PENDIENTE));
  assertTrue(salida.getAll().contains("Notificando a E-1")); assertTrue(salida.getAll().contains("Auditoria: solicitud"));
  assertEquals(nuevo==EstadoSolicitud.APROBADA,salida.getAll().contains("Contabilidad: programar pago"));
  assertEquals(nuevo,repositorio.findById(s.getId()).orElseThrow().getEstado());
 }
}
