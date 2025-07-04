package com.zona_fit.servicio;

import com.zona_fit.modelo.Cliente;
import com.zona_fit.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación concreta de los servicios de gestión de clientes
 * Actúa como capa intermedia entre el controlador y el repositorio
 * Contiene la lógica de negocio de la aplicación
 */
@Service // Marca la clase como un servicio de Spring (componente de negocio)
public class ClienteServicio implements IClienteServicio{

    @Autowired // Inyección de dependencias automática de Spring
    private ClienteRepositorio clienteRepositorio;

    /**
     * Obtiene todos los clientes de la base de datos
     * @return Lista completa de clientes
     */
    @Override
    public List<Cliente> listarClientes() {
        // Delega la operación al repositorio
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    /**
     * Busca un cliente por su ID único
     * @param idCliente ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
        // orElse(null) maneja el caso cuando no se encuentra el cliente
        Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
        return cliente;
    }

    /**
     * Guarda o actualiza un cliente en la base de datos
     * Spring Data JPA determina automáticamente si es INSERT o UPDATE
     * basándose en si el ID es null (nuevo) o tiene valor (actualización)
     * @param cliente Cliente a guardar/actualizar
     */
    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    /**
     * Elimina un cliente de la base de datos
     * @param cliente Cliente a eliminar (debe tener ID válido)
     */
    @Override
    public void eliminarClientePorId(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}