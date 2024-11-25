package com.pupuseriajenny.ordenes.utils;

import com.auth0.android.jwt.JWT;

public class JWTUtil {

    // Método para obtener el idEmpleado desde el JWT
    public static int obtenerIdEmpleadoDesdeJWT(String token) {
        try {
            JWT jwt = new JWT(token);  // Decodificamos el JWT
            // Obtener el ID del empleado del claim "idEmpleado"
            return jwt.getClaim("idEmpleado").asInt();  // Cambiar "idEmpleado" si es necesario
        } catch (Exception e) {
            // Si ocurre un error, capturamos la excepción y retornamos un valor predeterminado
            e.printStackTrace();
            return -1;  // Retornar -1 si no se puede obtener el ID
        }
    }

    // Método para obtener cualquier claim del JWT de manera reutilizable
    public static String obtenerClaimDesdeJWT(String token, String claimName) {
        try {
            JWT jwt = new JWT(token);  // Decodificamos el JWT
            // Obtener el claim solicitado
            return jwt.getClaim(claimName).asString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Retornar null si no se puede obtener el claim
        }
    }
}