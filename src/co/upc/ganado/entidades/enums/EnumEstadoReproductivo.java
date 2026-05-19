package co.upc.ganado.entidades.enums;

/**
 *Estado reproductivo actual de la hembra dentro del hato.
 *Valores: "vientre", "escotera", "parida", "descarte".
 */
public enum EnumEstadoReproductivo {
    /** Hembra en capacidad reproductiva */
    Vientre,
    /** Hembra joven aún no apta para reproducción */
    Escotera,
    /** Hembra que acaba de parir */
    Parida,
    /** Hembra descartada del programa reproductivo */
    Descarte
}
