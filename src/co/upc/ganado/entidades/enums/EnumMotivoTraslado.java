package co.upc.ganado.entidades.enums;

/**
 *Razón por la que se mueve ganado entre fincas.
 *Valores: "alimentacion", "reproduccion", "venta", "otro".
 */
public enum EnumMotivoTraslado {
    /** Traslado por alimentación o pastoreo */
    Alimentacion,
    /** Traslado por temporada de monta o reproducción */
    Reproduccion,
    /** Traslado asociado a una venta */
    Venta,
    /** Traslado por otra razón no clasificada */
    Otro
}
