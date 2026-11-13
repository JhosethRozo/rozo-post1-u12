# Reembolsos Integrado — Unidad 12

Autor: Jhoseth Esneider Rozo Carrillo. Proyecto integrador de Patrones de Diseño de Software.

## Diseño Propuesto

Esta sección se versiona con el dominio antes de implementar los patrones.

| Requisito | Punto de variabilidad | Patrón | Justificación en una oración |
| --- | --- | --- | --- |
| RF1 | Verificaciones independientes que pueden crecer y detener el procesamiento | Chain of Responsibility | Cada eslabón comprueba una condición y solo delega cuando es válida, permitiendo agregar verificaciones sin modificar las existentes. |
| RF2 | Fórmula de monto aprobado según categoría | Strategy | Cada categoría aporta un algoritmo intercambiable seleccionado por su clave, sin mezclar sus fórmulas en el servicio. |
| RF3 | Interesados y reacciones ante cambios de estado | Observer | El servicio comunica el cambio a una colección de observadores mediante una interfaz y cada interesado decide cómo reaccionar. |

La persistencia será JpaRepository sobre el dominio, sin puerto intermedio. Para respetar simultáneamente
ese contrato y el dominio sin imports de JPA, el mapeo se declarará externamente en META-INF/orm.xml.
Las notificaciones serán síncronas, como la lista del enunciado: todos los interesados participan
en el mismo cambio, sin prometer ejecución paralela ni entrega distribuida.

## Decisiones de diseño

### 1. Orden de validación

Documentos → monto → duplicados. Las dos primeras comprobaciones son constantes y el recorrido
de pendientes es O(n); detenerse antes ahorra ese recorrido. Colocar duplicados primero daría prioridad
a ese motivo, pero pagaría el costo aun cuando faltaran documentos. El servicio carga la lista antes
de validar, como el ejemplo: este orden ahorra comparación, no la consulta SQL previa.

### 2. Selección de calculadora

Un mapa construido con List<CalculadoraReembolso> inyectada evita modificar el selector cuando
se registra otra estrategia. Un switch exige editarlo por cada categoría. El enum sí debe ampliarse
si aparece BIENESTAR: no se promete extensión sin cambiar absolutamente ningún archivo, sino sin
modificar las fórmulas ni el selector existentes. Las claves duplicadas fallan al construir el mapa.

### 3. Decisión arquitectónica

Se conservan capas simples para persistencia, pues no hay plan de sustituir JPA ni un horizonte
documentado de tres años con cambios de infraestructura. La API externa anunciada por Contabilidad
sí es real, pero ObservadorCambioEstado ya permite cambiar esa implementación sin tocar el servicio.
El [ADR-001](docs/adr/ADR-001.md) desarrolla alternativas, consecuencias positivas/negativas y señales
para reconsiderarlas. El acoplamiento de aplicación a Spring Data es deliberado, no Hexagonal completa.

### 4. Límites de ArchUnit y estereotipos de Spring

@Component permite descubrir colaboradores y no ejecuta consultas ni introduce transporte web.
Se permite org.springframework.stereotype y se prohíben Spring Data, JPA, infraestructura y web
desde procesamiento. Prohibir todo Spring contradiría los componentes requeridos sin mejorar el
aislamiento de las reglas de negocio frente a persistencia/web.

Se ajustan dos defectos del ejemplo para que las reglas comprueben el contrato real: la tercera
selecciona clases Calculadora*, pues SelectorCalculadora no es una estrategia; la segunda permite
el acceso del controlador a sus propios campos/constructor, sin habilitar acceso al repositorio.
Las reglas importan solo producción y Surefire incluye explícitamente ReglasArquitectura en verify:
su nombre no coincide con las convenciones predeterminadas de *Test.

## Resumen de ADR-001

Persistencia directa mediante JpaRepository; dominio puro con mapeo externo XML; notificación
por Observer sin un bus adicional. Se acepta tener que introducir un puerto si aparece una alternativa
real a JPA. Observer permite sustituir un notificador, pero no aporta reintentos ni entrega garantizada.

## Qué se descartó y por qué

| Alternativa considerada | Motivo concreto del descarte hoy | Señal para reconsiderarla |
| --- | --- | --- |
| ApplicationEventPublisher y @EventListener | Tres observadores conocidos ya están desacoplados por una interfaz; el bus agrega indirección y dificulta seguir quién reacciona (KISS). | Suscripciones entre módulos independientes o necesidades transaccionales de eventos documentadas. |
| Visitor/Abstract Factory para exportadores | RF1-RF3 no piden exportación, ni siquiera CSV: el ejemplo de la guía no crea un requisito nuevo; no se cumple Rule of Three. | Requisitos reales de varios formatos con variación estable y repetida. |
| Una excepción por cada validador (decisión propia) | Todos los errores de validación tienen la misma reacción HTTP 400 y solo cambia el motivo; una jerarquía no produce comportamiento distinto (YAGNI/test de explicabilidad). | Clientes que deban recuperar cada error de forma diferente, con códigos y contratos independientes. |
| State para transiciones (decisión propia) | No hay una matriz de transiciones ni acciones de entrada/salida especificadas; añadirla ahora inventaría restricciones. | Finanzas define transiciones permitidas, reversión y reglas por estado. |
