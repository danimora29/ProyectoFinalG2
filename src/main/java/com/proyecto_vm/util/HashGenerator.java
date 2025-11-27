package com.proyecto_vm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad temporal para generar hashes de contraseñas
 * Ejecuta el método main para generar los hashes
 */
public class HashGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("=== Hashes BCrypt para contraseñas ===");
        System.out.println();
        System.out.println("admin123:");
        System.out.println(encoder.encode("admin123"));
        System.out.println();
        System.out.println("doctor123:");
        System.out.println(encoder.encode("doctor123"));
        System.out.println();
        System.out.println("usuario123:");
        System.out.println(encoder.encode("usuario123"));
        System.out.println();
        System.out.println("=== Copia estos hashes y úsalos en el script SQL ===");
    }
}

