# ganado_db

Sistema de Gestión Integral y Trazabilidad del Ganado — Universidad Popular del Cesar.

**Programación II (SS300)** — Momento 1. Proyecto individual.
**Estudiante:** Santiago Rafael Zuleta Neira

## Stack

- Java 25 (JDK estándar, sin dependencias externas)
- Swing (GUI)
- Ant (build system, NetBeans)
- CSV (persistencia archivos planos)

## Arquitectura

4 capas bajo `co.upc.ganado`:

- `entidades` — clases del modelo de dominio
- `datos` — persistencia CSV (lectura/escritura)
- `servicios` — lógica de negocio + ArrayList
- `presentacion` — GUI Swing + Main

## Build & Run

| Acción | Comando |
|---|---|
| Compilar | `ant build` |
| Ejecutar | `ant run` |
| Limpiar | `ant clean` |
| JAR | `ant jar` → `dist/ganado_db.jar` |

## Progreso (Fase 7)

- [x] FincaPanel (CRUD)
- [x] HembraPanel (CRUD)
- [x] MainFrame (JTabbedPane + botones compartidos)
- [x] LoginFrame (autenticación)
- [ ] MachoPanel
- [ ] VacunaPanel
- [ ] CompraPanel
- [ ] VentaPanel
- [ ] TrasladoPanel
- [ ] PalpacionPanel
- [ ] ConsultasPanel
