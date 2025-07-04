package com.zona_fit.repositorio;

import com.zona_fit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones CRUD de la entidad Cliente
 * Extiende JpaRepository que proporciona métodos básicos como:
 * - findAll(): obtener todos los clientes
 * - findById(): buscar cliente por ID
 * - save(): guardar/actualizar cliente
 * - delete(): eliminar cliente
 * - count(): contar registros
 */
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    // JpaRepository<Cliente, Integer> significa:
    // - Cliente: tipo de entidad que maneja
    // - Integer: tipo de dato de la clave primaria (id)
    
    // No necesita implementación, Spring Data JPA la genera automáticamente
    // Se pueden agregar métodos personalizados si es necesario
}