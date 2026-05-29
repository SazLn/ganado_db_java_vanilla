package co.upc.ganado.servicios;

import co.upc.ganado.datos.CsvUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Servicio de autenticacion de usuarios. Lee las credenciales desde
 * el archivo data/usuarios.csv y valida el ingreso al sistema.
 *
 * @author Santiago Rafael Zuleta Neira
 */
public class LoginService {
    private static final String RUTA = "data/usuarios.csv";

    /**
     * Valida las credenciales de un usuario contra el archivo CSV.
     * Carga los datos, los almacena en un HashMap y compara usuario
     * y contrasena.
     *
     * @param usuario    Nombre de usuario ingresado
     * @param contrasena Contrasena ingresada
     * @return true si las credenciales coinciden, false en caso contrario
     */
    public boolean validar(String usuario, String contrasena) {
        List<String[]> lineasCsv = CsvUtil.leerCsv(RUTA);
        HashMap<String, String> datosUsuario = new HashMap<>();

        // Llenar el HashMap con usuario → contrasena
        for (int i = 1; i < lineasCsv.size(); i++) {
            String[] datos = lineasCsv.get(i);
            datosUsuario.put(datos[0], datos[1]);
        }

        // Verificar si el usuario existe
        if (!datosUsuario.containsKey(usuario)) {
            System.out.println("El usuario ingresado es invalido. Intentelo nuevamente.");
            return false;
        }

        // Verificar si la contrasena coincide
        if (!contrasena.equals(datosUsuario.get(usuario))) {
            System.out.println("La contrasena ingresada es invalida. Intentelo nuevamente.");
            return false;
        }

        return true;
    }
}
