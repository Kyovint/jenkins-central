// src/com/kyodev/Utils.groovy
package com.kyodev

class Utils {
    // Método estático para leer un archivo de resources
    static String leerArchivo(steps, String ruta) {
        try {
            // libraryResource es un paso de Jenkins que carga archivos de /resources
            return steps.libraryResource(ruta)
        } catch (Exception e) {
            steps.echo "❌ Error al leer ${ruta}: ${e.getMessage()}"
            return "Archivo no encontrado"
        }
    }

    // Método estático para saludar
    static void saludar(steps, String nombre) {
        String mensaje = leerArchivo(steps, 'hola.txt')
        steps.echo "👋 ${mensaje}"
        steps.echo "Hola, ${nombre}! Bienvenido al CI/CD."
    }
}