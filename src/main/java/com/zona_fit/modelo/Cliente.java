package com.zona_fit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Entidad que representa un cliente del gimnasio ZonaFit
 * Mapea la tabla 'cliente' en la base de datos
 */
@Entity // Marca la clase como entidad JPA
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
@NoArgsConstructor // Constructor sin parámetros requerido por JPA
@AllArgsConstructor // Constructor con todos los parámetros
@ToString // Genera método toString()
@EqualsAndHashCode // Genera métodos equals() y hashCode()
public class Cliente {
    
    @Id // Marca el campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento en BD
    private Integer id; // ID único del cliente
    
    private String nombre; // Nombre del cliente
    private String apellido; // Apellido del cliente
    private Integer membresia; // Número de membresía del cliente

    /**
     * Constructor para crear cliente sin ID (para nuevos registros)
     * El ID se genera automáticamente en la base de datos
     */
    public Cliente(String nombre, String apellido, Integer membresia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.membresia = membresia;
    }
}