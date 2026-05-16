# AGENTS.md — ganado_db

## Communication Rules (MANDATORY)

**REGLA FUNDAMENTAL**: Responder de manera **directa, concisa y sin palabrería innecesaria**. Ir directo al punto.

- **Estructura por defecto**: 1) Solución/código directo, 2) Explicación mínima solo si es crítico, 3) NO agregar contexto innecesario
- **Solo profundizar** cuando el orquestador lo pida explícitamente ("explica en detalle", "profundiza", "dame más contexto")
- **Código**: mínimo código funcional indispensable, comentado de forma detallada pero concisa
- **Debugging**: formato `Causa: [razón en una línea]` + `Fix: [código o instrucción directa]`
- **Conceptual**: definición de 1-2 líneas + ejemplo si aplica
- **El usuario escribe el código manualmente** — el agente solo brinda apoyo y guía
- **NO** explicaciones educativas de conceptos a menos que se pidan explícitamente

## Project

NetBeans Java 25 project. Entry point: `Main` class.
Build system: Ant (NetBeans-generated `build.xml`).
Student: Santiago Rafael Zuleta Neira — Universidad Popular del Cesar.
Course: Programación II (SS300), Momento 1. Individual project.

## Commands

| Action | Command |
|---|---|
| Build | `ant build` (or NetBeans: Build Project) |
| Run | `ant run` (or NetBeans: Run Project) |
| Clean | `ant clean` |
| Clean + Build | `ant clean build` |
| JAR | `ant jar` → `dist/ganado_db.jar` |

Source: `src/` | Tests: `test/` (not used) | Encoding: UTF-8

## Package Structure

All code under `co.upc.ganado` (4-layer architecture):

```
src/
└── co/upc/ganado/
    ├── entidades/      # Domain model classes
    ├── datos/          # File persistence (CSV read/write)
    ├── servicios/      # Business logic + ArrayList collections
    └── presentacion/   # Swing GUI + Main entry point
```

- `Main` lives in `presentacion` and launches the GUI.
- Every class must have Javadoc-style comments.
- Naming: `camelCase` methods/variables, `PascalCase` classes, `lowercase` packages.

## Architecture Decisions

| Decision | Choice |
|---|---|
| GUI Framework | **Swing** (JDK standard, no external deps) |
| File Format | **CSV** — one file per entity, one line per object, comma-separated |
| Collections | **ArrayList per service** — each `XxxService` owns its `ArrayList<Xxx>` |
| Login | **File-based** — credentials stored in a CSV file |
| Sample Data | **Pre-loaded CSV files** — converted from SQL INSERTs |
| Debug Mode | Console mode for debugging, GUI is the main interface |

## Domain Classes (from SQL schema)

### Core entities

| SQL Table | Java Class | Key Attributes |
|---|---|---|
| FINCA | `Finca` | idFinca, nombreFinca, ubicacion |
| GANADO | `Ganado` (abstract parent) | idGanado, numeroMarca, fechaNacimiento, sexo, peso, estadoSalud, estadoVida, fechaSalida, motivoSalida, idFinca, idMadre, observaciones |
| GANADO_MACHO | `Macho extends Ganado` | calidadReproductiva, esPadrote, fechaInicioPadrote, fechaFinPadrote |
| GANADO_HEMBRA | `Hembra extends Ganado` | estadoReproductivoActual, fechaUltimoParto, numeroPartos, aptaParaReproduccion |

### Transaction entities

| SQL Table | Java Class | Key Attributes |
|---|---|---|
| COMPRA | `Compra` | idCompra, fechaCompra, tipoCompra, proveedor, documentoReferencia, valorTotalCompra, responsableCompra |
| DETALLE_COMPRA | `DetalleCompra` (associative) | idCompra, idGanado, precioIndividual, observaciones |
| VENTA | `Venta` | idVenta, fechaVenta, tipoVenta, comprador, documentoReferencia, valorTotalVenta, metodoPago, responsableVenta |
| DETALLE_VENTA | `DetalleVenta` (associative) | idVenta, idGanado, precioIndividual, pesoAlMomentoVenta, observaciones |

### Health/reproduction entities

| SQL Table | Java Class | Key Attributes |
|---|---|---|
| PALPACION | `Palpacion` | idPalpacion, fechaPalpacion, tipoPalpacion, observacionesGenerales |
| RESULTADO_PALPACION | `ResultadoPalpacion` (associative) | idGanado, idPalpacion, resultados, fechaConcepcionAprox, fechaPartoEstimada |
| VACUNA | `Vacuna` | idVacuna, nombreVacuna, descripcion, dosisEstandar |
| APLICACION_VACUNA | `AplicacionVacuna` (associative) | idGanado, idVacuna, fechaAplicacion, dosisAplicada, responsableVacunacion |

### Logistics entities

| SQL Table | Java Class | Key Attributes |
|---|---|---|
| TRASLADO | `Traslado` | idTraslado, fechaTraslado, motivoTraslado, medioTransporte, responsableTraslado, costoTraslado, idFincaOrigen, idFincaDestino |
| DETALLE_TRASLADO | `DetalleTraslado` (associative) | idTraslado, idGanado, observaciones |

### Enums (SQL ENUM → Java enum)

| SQL ENUM | Java Enum | Values |
|---|---|---|
| TIPO_SEXO | `Sexo` | M, H |
| TIPO_ESTADO_VIDA | `EstadoVida` | Activo, Vendido, Muerto |
| TIPO_ESTADO_SALUD | `EstadoSalud` | Sano, Enfermo |
| TIPO_MOTIVO_SALIDA | `MotivoSalida` | Venta, Muerte |
| TIPO_CALIDAD_REPRODUCTIVA | `CalidadReproductiva` | Buena, Regular, Mala, Pendiente |
| TIPO_ESTADO_REPRODUCTIVO | `EstadoReproductivo` | Vientre, Escotera, Parida, Descarte |
| TIPO_RESULTADO_PALPACION | `ResultadoPalpacionTipo` | Vacia, Prenada |
| TIPO_TRANSACCION | `TipoTransaccion` | Verbal, Documentada |
| TIPO_MOTIVO_TRASLADO | `MotivoTraslado` | Alimentacion, Reproduccion, Venta, Otro |
| TIPO_PALPACION | `TipoPalpacion` | Manual, Ecografia |

## CSV File Format

Each entity maps to one CSV file in a `data/` directory.

**Example — ganado.csv:**
```
idGanado,numeroMarca,fechaNacimiento,sexo,peso,estadoSalud,estadoVida,fechaSalida,motivoSalida,idFinca,idMadre,observaciones
1,909-19,2019-05-10,M,875.00,Sano,Activo,,,1,,
```

- First line = header (attribute names).
- Empty fields = nullable attributes (empty string between commas).
- Dates = `YYYY-MM-DD` format.
- Money = decimal with 2 places.
- Boolean = `true`/`false`.

**CSV file list (14 files):**
`finca.csv`, `ganado.csv`, `macho.csv`, `hembra.csv`, `compra.csv`, `detalleCompra.csv`, `venta.csv`, `detalleVenta.csv`, `palpacion.csv`, `resultadoPalpacion.csv`, `vacuna.csv`, `aplicacionVacuna.csv`, `traslado.csv`, `detalleTraslado.csv`

## SQL Queries → Java Methods

The 7 SQL views/queries must be replicated as Java methods using ArrayList iteration:

| # | Query Name | SQL Tables Involved | Java Service Method |
|---|---|---|---|
| 1 | Trazabilidad de animal | DETALLE_TRASLADO, TRASLADO, GANADO, FINCA | `TrasladoService.getTrazabilidadAnimal(int idGanado)` |
| 2 | Estado reproductivo del hato | GANADO_HEMBRA, GANADO (COUNT + GROUP BY) | `HembraService.getEstadoReproductivoHato()` |
| 3 | Historial de palpaciones | RESULTADO_PALPACION, GANADO, PALPACION | `PalpacionService.getHistorialPalpaciones(int idGanado)` |
| 4 | Control de vacunación | APLICACION_VACUNA, GANADO, VACUNA | `VacunaService.getControlVacunacion()` |
| 5 | Inventario por finca | FINCA, GANADO (COUNT + CASE WHEN) | `FincaService.getInventarioPorFinca()` |
| 6 | Historial de compras | COMPRA, DETALLE_COMPRA, GANADO | `CompraService.getHistorialCompras()` |
| 7 | Historial de padrotes | GANADO_MACHO, GANADO | `MachoService.getHistorialPadrotes()` |

## Key Constraints

- **Must compile.** Non-compiling code = automatic fail.
- **GUI required.** Swing only (no external libs in `javac.classpath`).
- **File persistence.** CRUD to CSV files, NO database.
- **ArrayList required.** All in-memory operations use `ArrayList`.
- **POO mandatory:** inheritance (`Macho`/`Hembra` extend `Ganado`), polymorphism, encapsulation, overloading, exceptions, class relationships.
- **Soft delete:** Ganado records are NEVER physically deleted. Use `estadoVida = Vendido/Muerto` instead.
- **No external dependencies.** JDK standard library only.

## Context Files

- `.contexto-proyecto/proyecto-aula-contexto.md` — full project requirements, rubric, status tracker.
- `.contexto-proyecto/creacion.sql` — SQL schema with CREATE TABLE, triggers, and INSERT data.
- `.contexto-proyecto/Consultas.sql` — 7 SQL queries/views to replicate in Java.
- `.contexto-proyecto/plan-desarrollo.md` — step-by-step development plan with 8 phases and progress checklist.
- `Entregable Borrador.pdf` (parent dir) — draft report with reusable content.

Read `proyecto-aula-contexto.md` before writing code. It contains the full evaluation rubric (5.0 points), document structure, and project status.
Follow `plan-desarrollo.md` for execution order — phases must be completed sequentially.
