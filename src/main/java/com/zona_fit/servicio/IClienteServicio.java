package com.zona_fit.servicio;

import com.zona_fit.modelo.Cliente;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de clientes
 * Implementa el patrón de diseño Strategy/Template Method
 * Permite cambiar la implementación sin afectar el código cliente
 */
public interface IClienteServicio {
    
    /**
     * Obtiene la lista completa de todos los clientes registrados
     * @return Lista de objetos Cliente
     */
    public List<Cliente> listarClientes();
    
    /**
     * Busca un cliente específico por su ID
     * @param idCliente ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarClientePorId(Integer idCliente);
    
    /**
     * Guarda un nuevo cliente o actualiza uno existente
     * Si el cliente tiene ID null, se crea nuevo registro
     * Si el cliente tiene ID, se actualiza el registro existente
     * @param cliente Objeto Cliente a guardar/actualizar
     */
    public void guardarCliente(Cliente cliente);
    
    /**
     * Elimina un cliente de la base de datos
     * @param cliente Objeto Cliente a eliminar
     */
    public void eliminarClientePorId(Cliente cliente);
}