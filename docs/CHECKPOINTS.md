# Checkpoints de la Unidad 12

| Etapa | Comprobación real | Evidencia |
| --- | --- | --- |
| Diseño previo | README con RF1-RF3 y justificación antes de patrones; commit dadae95 | [Diseño inicial](https://github.com/JhosethRozo/rozo-post1-u12/commit/dadae95), [dominio puro](evidencias/02-dominio.png) |
| Base | mvn clean package sin patrones ni imports Spring/JPA en dominio | [Log](evidencias/01-base.log), [captura](evidencias/01-base.png) |
| RF1 | Cadena documentos → monto → duplicados; sin persistencia en validadores | [Código real](evidencias/03-validacion.png), tres clases unitarias |
| RF2 | Tres estrategias, selección por lista inyectada y mapa | [Código real](evidencias/03-calculo.png), seis casos parametrizados de tarifas |
| RF3 | Tres observadores reciben cambios; solo APROBADA produce pago | [Código real](evidencias/03-notificacion.png), [log de servidor](evidencias/05-servidor.png) |
| REST | Tres categorías crean con monto correcto; PUT notifica; sin documentos devuelve 400 | [VIAJE](evidencias/05-rest-VIAJE.png), [REPRESENTACION](evidencias/05-rest-REPRESENTACION.png), [CAPACITACION](evidencias/05-rest-CAPACITACION.png), [rechazo](evidencias/05-rest-sin-documentos.png), [respuestas JSON](evidencias/05-rest.json) |
| Parte 1 | 21 pruebas funcionales y checkpoint publicado antes de la Parte 2 | [Pruebas](evidencias/06-parte1.png), [etiqueta](https://github.com/JhosethRozo/rozo-post1-u12/tree/parte-1-funcional) |
| ADR | Persistencia y notificación evaluadas por separado, alternativas y consecuencias | [ADR-001](adr/ADR-001.md) |
| ArchUnit | Cuatro reglas ejecutadas sin omisiones ni fallos | [Log](evidencias/08-archunit.log), [captura](evidencias/08-archunit.png) |
| CI e impacto | Java 17, reglas y suite; tres alternativas de referencia/propias con señales futuras | [Workflow](../.github/workflows/arquitectura.yml), [README](../README.md) |
| Violación | Campo de tipo repositorio en procesamiento; falla exactamente Regla 1 | [Actions rojo](evidencias/10-actions-fallo.png), [log remoto](evidencias/10-actions-fallo.log), [detección local](evidencias/10-violacion-local.png) |
| Revert | git revert retira la dependencia, sin debilitar reglas; 25 pruebas pasan | [Actions verde](evidencias/11-actions-verde.png), [verify](evidencias/11-verify-revert.png) |
| Cierre | README en orden: diseño, decisiones 1-4, ADR, descartes; commits por parte | [Historial](https://github.com/JhosethRozo/rozo-post1-u12/commits/main) |

Los JSON de Actions incluyen el SHA exacto y la conclusión obtenida de la API pública. Las imágenes
de comandos son capturas de un visor de logs reales, sin modificar sus resultados. El fallo intencional
se conserva porque es un entregable obligatorio y se distingue del estado final aprobado.

## Correspondencia con la rúbrica

- Implementación (40 %): diseño previo, tres patrones diferentes, dominio puro, ADR y ArchUnit coherentes.
- Funcionalidad (25 %): tres categorías, validaciones, observadores, 25 pruebas y secuencia remota rojo → revert → verde.
- Documentación (20 %): cuatro decisiones concretas, ADR completo, descartes propios y capturas enlazadas.
- Estilo (15 %): formato Java uniforme, Javadoc en contratos de patrones, paquetes separados, reglas descriptivas y fecha real en ADR.
