# Plan de Desarrollo — Sistema de Gestión Ganadero

> **Creado:** 15 de mayo de 2026
> **Estado:** Por iniciar

---

## Orden de Ejecución y Justificación

```
1. Enums          → Base de tipos que usan TODAS las entidades
2. Entidades      → Modelo de dominio que usan TODAS las capas
3. Capa Datos     → Persistencia CSV que necesitan los servicios
4. CSVs ejemplo   → Datos iniciales para que los servicios tengan qué cargar
5. Servicios      → Lógica + ArrayList + 7 consultas (usa entidades + datos)
6. Login          → Control de acceso (usa servicios)
7. GUI            → Interfaz final (usa servicios + login)
8. Pruebas        → Validación de todo integrado
```

**Regla:** Cada fase es prerequisito de la siguiente. No se salta ninguna.

---

## Fase 1: Enums (10 archivos)

**Paquete:** `co.upc.ganado.entidades`

| # | Archivo | Valores |
|---|---|---|
| 1.1 | `Sexo.java` | M, H |
| 1.2 | `EstadoVida.java` | Activo, Vendido, Muerto |
| 1.3 | `EstadoSalud.java` | Sano, Enfermo |
| 1.4 | `MotivoSalida.java` | Venta, Muerte |
| 1.5 | `CalidadReproductiva.java` | Buena, Regular, Mala, Pendiente |
| 1.6 | `EstadoReproductivo.java` | Vientre, Escotera, Parida, Descarte |
| 1.7 | `ResultadoPalpacionTipo.java` | Vacia, Prenada |
| 1.8 | `TipoTransaccion.java` | Verbal, Documentada |
| 1.9 | `MotivoTraslado.java` | Alimentacion, Reproduccion, Venta, Otro |
| 1.10 | `TipoPalpacion.java` | Manual, Ecografia |

**Convención:** Cada enum con Javadoc, valores en mayúsculas según SQL.

---

## Fase 2: Entidades (14 clases)

**Paquete:** `co.upc.ganado.entidades`

### Clases base

| # | Archivo | Tipo | Dependencias |
|---|---|---|---|
| 2.1 | `Finca.java` | Clase concreta | Ninguna |
| 2.2 | `Ganado.java` | **Abstracta** | Sexo, EstadoSalud, EstadoVida, MotivoSalida, Finca |

### Herencia

| # | Archivo | Extiende | Enums usados |
|---|---|---|---|
| 2.3 | `Macho.java` | Ganado | CalidadReproductiva |
| 2.4 | `Hembra.java` | Ganado | EstadoReproductivo |

### Transacciones

| # | Archivo | Tipo | Enums usados |
|---|---|---|---|
| 2.5 | `Compra.java` | Cabecera | TipoTransaccion |
| 2.6 | `DetalleCompra.java` | Asociativa | Ninguno |
| 2.7 | `Venta.java` | Cabecera | TipoTransaccion |
| 2.8 | `DetalleVenta.java` | Asociativa | Ninguno |

### Sanidad/Reproducción

| # | Archivo | Tipo | Enums usados |
|---|---|---|---|
| 2.9 | `Palpacion.java` | Cabecera | TipoPalpacion |
| 2.10 | `ResultadoPalpacion.java` | Asociativa | ResultadoPalpacionTipo |
| 2.11 | `Vacuna.java` | Catálogo | Ninguno |
| 2.12 | `AplicacionVacuna.java` | Asociativa | Ninguno |

### Logística

| # | Archivo | Tipo | Enums usados |
|---|---|---|---|
| 2.13 | `Traslado.java` | Cabecera | MotivoTraslado |
| 2.14 | `DetalleTraslado.java` | Asociativa | Ninguno |

**Cada entidad debe incluir:**
- Atributos `private` (encapsulamiento)
- Constructor vacío
- Constructor con todos los parámetros
- Getters y setters
- `@Override toString()`
- Javadoc en clase y cada método
- Sobrecarga de constructores si aplica (POO)

---

## Fase 3: Capa de Datos — Persistencia CSV (15 archivos)

**Paquete:** `co.upc.ganado.datos`

### Utilidad genérica

| # | Archivo | Métodos |
|---|---|---|
| 3.1 | `CsvUtil.java` | `leerLineas(String)`, `escribirLineas(String, List<String>)`, `parsearLinea(String, String)` |

### Clases de acceso por entidad

| # | Archivo | Entidad |
|---|---|---|
| 3.2 | `FincaData.java` | Finca |
| 3.3 | `GanadoData.java` | Ganado |
| 3.4 | `MachoData.java` | Macho |
| 3.5 | `HembraData.java` | Hembra |
| 3.6 | `CompraData.java` | Compra |
| 3.7 | `DetalleCompraData.java` | DetalleCompra |
| 3.8 | `VentaData.java` | Venta |
| 3.9 | `DetalleVentaData.java` | DetalleVenta |
| 3.10 | `PalpacionData.java` | Palpacion |
| 3.11 | `ResultadoPalpacionData.java` | ResultadoPalpacion |
| 3.12 | `VacunaData.java` | Vacuna |
| 3.13 | `AplicacionVacunaData.java` | AplicacionVacuna |
| 3.14 | `TrasladoData.java` | Traslado |
| 3.15 | `DetalleTrasladoData.java` | DetalleTraslado |

**Patrón de cada `XxxData`:**
- Constante `RUTA_ARCHIVO` → `"data/xxx.csv"`
- `List<Xxx> cargarTodo()` → lee CSV, parsea, retorna lista
- `void guardarTodo(List<Xxx>)` → sobrescribe CSV desde lista
- `void insertar(Xxx)` → agrega a lista + guarda
- `void actualizar(Xxx)` → reemplaza en lista + guarda
- `void eliminar(int id)` → NO para Ganado (soft delete), sí para las demás

---

## Fase 4: Archivos CSV con Datos de Ejemplo (14 archivos)

**Ubicación:** `data/`

| # | Archivo | Registros | Origen SQL |
|---|---|---|---|
| 4.1 | `finca.csv` | 3 | INSERT Finca |
| 4.2 | `ganado.csv` | 36 | INSERT Ganado |
| 4.3 | `macho.csv` | 22 | INSERT Ganado_Macho |
| 4.4 | `hembra.csv` | 14 | INSERT Ganado_Hembra |
| 4.5 | `compra.csv` | 3 | INSERT Compra |
| 4.6 | `detalleCompra.csv` | 26 | INSERT Detalle_Compra |
| 4.7 | `venta.csv` | 2 | INSERT Venta |
| 4.8 | `detalleVenta.csv` | 3 | INSERT Detalle_Venta |
| 4.9 | `palpacion.csv` | 2 | INSERT Palpacion |
| 4.10 | `resultadoPalpacion.csv` | 5 | INSERT Resultado_Palpacion |
| 4.11 | `vacuna.csv` | 4 | INSERT Vacuna |
| 4.12 | `aplicacionVacuna.csv` | ~12 | INSERT Aplicacion_Vacuna |
| 4.13 | `traslado.csv` | 7 | INSERT Traslado |
| 4.14 | `detalleTraslado.csv` | ~16 | INSERT Detalle_Traslado |

**Formato:** header en primera línea, campos separados por coma, fechas `YYYY-MM-DD`, decimales con 2 places, booleanos `true`/`false`, campos nulos = vacío entre comas.

---

## Fase 5: Capa de Servicios — Lógica de Negocio (14 archivos)

**Paquete:** `co.upc.ganado.servicios`

| # | Archivo | Entidad | Consulta Especial |
|---|---|---|---|
| 5.1 | `FincaService.java` | Finca | `getInventarioPorFinca()` (Consulta 5) |
| 5.2 | `GanadoService.java` | Ganado | Operaciones comunes |
| 5.3 | `MachoService.java` | Macho | `getHistorialPadrotes()` (Consulta 7) |
| 5.4 | `HembraService.java` | Hembra | `getEstadoReproductivoHato()` (Consulta 2) |
| 5.5 | `CompraService.java` | Compra | `getHistorialCompras()` (Consulta 6) |
| 5.6 | `DetalleCompraService.java` | DetalleCompra | — |
| 5.7 | `VentaService.java` | Venta | — |
| 5.8 | `DetalleVentaService.java` | DetalleVenta | — |
| 5.9 | `PalpacionService.java` | Palpacion | `getHistorialPalpaciones(int)` (Consulta 3) |
| 5.10 | `ResultadoPalpacionService.java` | ResultadoPalpacion | — |
| 5.11 | `VacunaService.java` | Vacuna | `getControlVacunacion()` (Consulta 4) |
| 5.12 | `AplicacionVacunaService.java` | AplicacionVacuna | — |
| 5.13 | `TrasladoService.java` | Traslado | `getTrazabilidadAnimal(int)` (Consulta 1) |
| 5.14 | `DetalleTrasladoService.java` | DetalleTraslado | — |

**Cada servicio incluye:**
- `ArrayList<Xxx> lista` — colección en memoria
- `XxxData data` — referencia a capa de datos
- Constructor: carga datos desde CSV
- `void guardar()` — persiste cambios a CSV
- CRUD: `agregar()`, `buscarPorId()`, `buscarTodos()`, `actualizar()`, `eliminar()` (soft delete para Ganado)
- Métodos de filtrado/búsqueda específicos
- Las 7 consultas replican la lógica del SQL usando iteración de ArrayList

---

## Fase 6: Login / Autenticación (3 archivos + 1 CSV)

| # | Archivo | Paquete | Contenido |
|---|---|---|---|
| 6.1 | `data/usuarios.csv` | — | usuario, contraseña, rol |
| 6.2 | `Usuario.java` | entidades | username, password, rol |
| 6.3 | `LoginService.java` | servicios | validar(username, password) → boolean |

---

## Fase 7: GUI — Presentación (14 archivos)

**Paquete:** `co.upc.ganado.presentacion`

| # | Archivo | Contenido |
|---|---|---|
| 7.1 | `LoginFrame.java` | Ventana login: campos usuario/contraseña, botón entrar |
| 7.2 | `MainFrame.java` | Ventana principal: menú lateral o tabs, navegación a cada panel |
| 7.3 | `FincaPanel.java` | JTable + formulario CRUD Finca |
| 7.4 | `GanadoPanel.java` | JTable + formulario CRUD Ganado (lista general) |
| 7.5 | `MachoPanel.java` | JTable + formulario CRUD Macho |
| 7.6 | `HembraPanel.java` | JTable + formulario CRUD Hembra |
| 7.7 | `CompraPanel.java` | JTable + formulario CRUD Compra + detalle |
| 7.8 | `VentaPanel.java` | JTable + formulario CRUD Venta + detalle |
| 7.9 | `TrasladoPanel.java` | JTable + formulario CRUD Traslado + detalle |
| 7.10 | `PalpacionPanel.java` | JTable + formulario CRUD Palpacion + resultados |
| 7.11 | `VacunaPanel.java` | JTable + formulario CRUD Vacuna + aplicaciones |
| 7.12 | `ConsultasPanel.java` | 7 consultas con filtros y tablas de resultados |
| 7.13 | `Main.java` | Entry point — `new LoginFrame().setVisible(true)` |

**Patrón de cada Panel:**
- `JTable` con `DefaultTableModel` para mostrar datos
- Botones: Nuevo, Editar, Eliminar, Guardar, Cancelar
- `JOptionPane` para formularios de entrada
- Instancia del servicio correspondiente
- `cargarTabla()` al iniciar y después de cada CRUD

---

## Fase 8: Pruebas y Validación Final

| # | Acción | Comando / Verificación |
|---|---|---|
| 8.1 | Compilación limpia | `ant clean build` — 0 errores |
| 8.2 | CRUD por entidad | Ejecutar y probar crear/editar/eliminar en cada panel |
| 8.3 | 7 consultas | Verificar resultados contra datos de ejemplo |
| 8.4 | Soft delete | Eliminar ganado → verificar estadoVida cambia, no se borra del CSV |
| 8.5 | Persistencia | Cerrar app → abrir → datos intactos |
| 8.6 | Login | Credenciales correctas → entra, incorrectas → rechaza |
| 8.7 | Generar JAR | `ant jar` → `dist/ganado_db.jar` ejecutable |

---

## Checklist de Progreso

### Fase 1: Enums
- [ ] Sexo.java
- [ ] EstadoVida.java
- [ ] EstadoSalud.java
- [ ] MotivoSalida.java
- [ ] CalidadReproductiva.java
- [ ] EstadoReproductivo.java
- [ ] ResultadoPalpacionTipo.java
- [ ] TipoTransaccion.java
- [ ] MotivoTraslado.java
- [ ] TipoPalpacion.java

### Fase 2: Entidades
- [ ] Finca.java
- [ ] Ganado.java (abstracta)
- [ ] Macho.java
- [ ] Hembra.java
- [ ] Compra.java
- [ ] DetalleCompra.java
- [ ] Venta.java
- [ ] DetalleVenta.java
- [ ] Palpacion.java
- [ ] ResultadoPalpacion.java
- [ ] Vacuna.java
- [ ] AplicacionVacuna.java
- [ ] Traslado.java
- [ ] DetalleTraslado.java

### Fase 3: Capa de Datos
- [ ] CsvUtil.java
- [ ] FincaData.java
- [ ] GanadoData.java
- [ ] MachoData.java
- [ ] HembraData.java
- [ ] CompraData.java
- [ ] DetalleCompraData.java
- [ ] VentaData.java
- [ ] DetalleVentaData.java
- [ ] PalpacionData.java
- [ ] ResultadoPalpacionData.java
- [ ] VacunaData.java
- [ ] AplicacionVacunaData.java
- [ ] TrasladoData.java
- [ ] DetalleTrasladoData.java

### Fase 4: CSVs de Ejemplo
- [ ] finca.csv
- [ ] ganado.csv
- [ ] macho.csv
- [ ] hembra.csv
- [ ] compra.csv
- [ ] detalleCompra.csv
- [ ] venta.csv
- [ ] detalleVenta.csv
- [ ] palpacion.csv
- [ ] resultadoPalpacion.csv
- [ ] vacuna.csv
- [ ] aplicacionVacuna.csv
- [ ] traslado.csv
- [ ] detalleTraslado.csv

### Fase 5: Servicios
- [ ] FincaService.java
- [ ] GanadoService.java
- [ ] MachoService.java
- [ ] HembraService.java
- [ ] CompraService.java
- [ ] DetalleCompraService.java
- [ ] VentaService.java
- [ ] DetalleVentaService.java
- [ ] PalpacionService.java
- [ ] ResultadoPalpacionService.java
- [ ] VacunaService.java
- [ ] AplicacionVacunaService.java
- [ ] TrasladoService.java
- [ ] DetalleTrasladoService.java

### Fase 6: Login
- [ ] usuarios.csv
- [ ] Usuario.java
- [ ] LoginService.java

### Fase 7: GUI
- [ ] LoginFrame.java
- [ ] MainFrame.java
- [ ] FincaPanel.java
- [ ] GanadoPanel.java
- [ ] MachoPanel.java
- [ ] HembraPanel.java
- [ ] CompraPanel.java
- [ ] VentaPanel.java
- [ ] TrasladoPanel.java
- [ ] PalpacionPanel.java
- [ ] VacunaPanel.java
- [ ] ConsultasPanel.java
- [ ] Main.java

### Fase 8: Pruebas
- [ ] Compilación limpia
- [ ] CRUD funcional
- [ ] 7 consultas verificadas
- [ ] Soft delete verificado
- [ ] Persistencia verificada
- [ ] Login funcional
- [ ] JAR generado
