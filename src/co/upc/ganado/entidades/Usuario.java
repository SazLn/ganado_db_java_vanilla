package co.upc.ganado.entidades;

/**
 * Representa un usuario del sistema con credenciales de acceso.
 * Cada usuario tiene un nombre de usuario y una contraseña para autenticarse
 * en la aplicacion.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class Usuario {
    private String nombreUsuario;
    private String contrasena;

    /**
     * Constructor completo.
     *
     * @param nombreUsuario Nombre de usuario para iniciar sesion
     * @param contrasena    Contrasena del usuario
     */
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    //GETTERS y SETTERS
    public String getNombreUsuario() {return nombreUsuario;}
    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario = nombreUsuario;}

    public String getContrasena() {return contrasena;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}

    //MÉTODOS

    /**
     * Devuelve una representacion en texto del usuario.
     *
     * @return Cadena con los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
