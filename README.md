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
