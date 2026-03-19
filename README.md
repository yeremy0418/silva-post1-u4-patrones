# apellido-post1-u4 - Patrones de Comportamiento

## Descripción
Este laboratorio implementa el backend de un sistema de e-commerce en Spring Boot 3.2. El flujo cubre validación secuencial de pedidos y ejecución de acciones reversibles sobre un pedido para modelar operaciones reales del negocio.

## Patrones implementados
### Chain of Responsibility
- Participantes: `ValidadorPedido` (handler abstracto), `ValidadorStock`, `ValidadorMonto`, `ValidadorCredito`.
- Flujo: un pedido recorre los validadores en orden (stock -> monto -> crédito). Si uno rechaza, la cadena se detiene y no se evalúan los siguientes.

### Command con Undo
- Participantes: `Comando` (interface), `ComandoConfirmar`, `ComandoAplicarDescuento`, `HistorialComandos` (Invoker con `Deque`).
- Flujo de undo: cada `execute()` guarda el estado anterior (`estadoAnterior` o `totalAnterior`). `deshacer()` hace `pop` del historial y ejecuta `undo()` para restaurar el estado original.

## Prerrequisitos
- Java 17+
- Maven 3.8+

## Instrucciones de ejecución
# Compilar y empaquetar
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run

# Ejecutar los tests
mvn test

## Salida de consola esperada
```text
--- Validando pedido P-001 ---
[STOCK] OK: 3 unidades disponibles.
[MONTO] OK: total $45000.0 supera el mínimo.
[CREDITO] OK: crédito del cliente aprobado.
Resultado validación: true
[CMD] Pedido P-001 confirmado.
[CMD] Descuento 10% aplicado: $45000.00 → $40500.00
Estado actual: Pedido{id='P-001', estado='CONFIRMADO', total=40500.0}

--- Deshaciendo última acción ---
[UNDO] Descuento revertido: $45000.00 restaurado
Estado después de undo: Pedido{id='P-001', estado='CONFIRMADO', total=45000.0}

--- Validando pedido P-002 (sin crédito) ---
[STOCK] OK: 2 unidades disponibles.
[MONTO] OK: total $30000.0 supera el mínimo.
[CREDITO] RECHAZADO: el cliente no tiene crédito aprobado.
```

## Estructura del proyecto
```text
pedidos-comportamiento/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── universidad/
    │               └── pedidos/
    │                   ├── PedidosApp.java
    │                   ├── modelo/
    │                   │   └── Pedido.java
    │                   ├── cor/
    │                   │   ├── ValidadorPedido.java
    │                   │   ├── ValidadorStock.java
    │                   │   ├── ValidadorMonto.java
    │                   │   └── ValidadorCredito.java
    │                   └── command/
    │                       ├── Comando.java
    │                       ├── ComandoConfirmar.java
    │                       ├── ComandoAplicarDescuento.java
    │                       └── HistorialComandos.java
    └── test/
        └── java/
            └── com/
                └── universidad/
                    └── pedidos/
                        └── CadenaValidacionTest.java
```
