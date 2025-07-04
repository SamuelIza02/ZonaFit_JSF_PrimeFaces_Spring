package com.zona_fit;

import com.zona_fit.modelo.Cliente;
import com.zona_fit.servicio.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal de la aplicación ZonaFit
 * Implementa CommandLineRunner para ejecutar código después del arranque de Spring
 * Proporciona una interfaz de consola para gestionar clientes del gimnasio
 */
@SpringBootApplication // Combina @Configuration, @EnableAutoConfiguration y @ComponentScan
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired // Inyección automática del servicio de clientes
	private IClienteServicio clienteServicio;

	// Logger para mostrar información en consola de forma profesional
	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	// Separador de líneas multiplataforma (Windows: \r\n, Unix: \n)
	String nl = System.lineSeparator();

	/**
	 * Método principal que inicia la aplicación Spring Boot
	 */
	public static void main(String[] args) {
		logger.info("Iniciando aplicacion");
		// Levantar el contexto de Spring y ejecutar la aplicación
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicacion finalizada");
	}

	/**
	 * Método ejecutado automáticamente después del arranque de Spring
	 * Inicia la interfaz de usuario por consola
	 */
	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	/**
	 * Método principal que controla el flujo de la aplicación
	 * Implementa un bucle de menú hasta que el usuario decida salir
	 */
	private void zonaFitApp() {
		logger.info(nl + "*** Aplicacion ZonaFit ***" + nl);
		boolean salir = false;
		Scanner consola = new Scanner(System.in);
		
		// Bucle principal del menú
		while (!salir) {
			int opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	/**
	 * Muestra el menú de opciones disponibles y captura la selección del usuario
	 * @param consola Scanner para leer entrada del usuario
	 * @return Opción seleccionada por el usuario
	 */
	private int mostrarMenu(Scanner consola) {
		logger.info("""
				1. Listar Clientes
				2. Agregar Cliente
				3. Buscar Cliente por ID
				4. Modificar Cliente
				5. Eliminar Cliente
				6. Salir
				Ingrese una opcion: \s""");
		return Integer.parseInt(consola.nextLine());
	}

	/**
	 * Ejecuta la acción correspondiente según la opción seleccionada
	 * @param consola Scanner para interacción con el usuario
	 * @param opcion Opción seleccionada del menú
	 * @return true si el usuario eligió salir, false en caso contrario
	 */
	private boolean ejecutarOpciones(Scanner consola, int opcion){
		boolean salir = false;
		
		// Switch expression (Java 14+) para manejar las opciones del menú
		switch (opcion){
			case 1 -> listarClientes();
			case 2 -> agregarCliente(consola);
			case 3 -> buscarClientePorId(consola);
			case 4 -> modificarCliente(consola);
			case 5 -> eliminarCliente(consola);
			case 6 -> {
				logger.info("Saliendo de la aplicacion" + nl);
				salir = true;
			}
			default -> logger.info("Opcion invalida");
		}
		return salir;
	}

	/**
	 * Lista todos los clientes registrados en el sistema
	 */
	private void listarClientes(){
		logger.info(nl + "--- Listando Clientes ---" + nl);
		List<Cliente> clientes = clienteServicio.listarClientes();
		
		// Uso de forEach con expresión lambda para mostrar cada cliente
		clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
	}

	/**
	 * Permite agregar un nuevo cliente al sistema
	 * @param consola Scanner para capturar datos del usuario
	 */
	private void agregarCliente(Scanner consola){
		logger.info(nl + "--- Agregar Cliente ---" + nl);
		
		// Captura de datos del nuevo cliente
		logger.info("Ingrese el nombre del cliente: ");
		String nombre = consola.nextLine();
		logger.info("Ingrese el apellido del cliente: ");
		String apellido = consola.nextLine();
		logger.info("Ingrese numero de membresia: ");
		int membresia = Integer.parseInt(consola.nextLine());
		
		// Crear cliente con ID null para que se genere automáticamente
		clienteServicio.guardarCliente(new Cliente(null, nombre, apellido, membresia));
		logger.info("Cliente agregado correctamente");
	}

	/**
	 * Busca y muestra un cliente específico por su ID
	 * @param consola Scanner para capturar el ID del cliente
	 */
	private void buscarClientePorId(Scanner consola){
		logger.info(nl + "--- Buscar Cliente por ID ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		
		Cliente cliente = clienteServicio.buscarClientePorId(id);
		
		// Validación de existencia del cliente
		if (cliente != null){
			logger.info("Cliente encontrado: " + cliente + nl);
		} else {
			logger.info("Cliente no encontrado: " + cliente + nl);
		}
	}

	/**
	 * Permite modificar los datos de un cliente existente
	 * @param consola Scanner para capturar nuevos datos del cliente
	 */
	private void modificarCliente(Scanner consola){
		logger.info(nl + "--- Modificar Cliente ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		
		// Verificar que el cliente existe antes de modificar
		if (clienteServicio.buscarClientePorId(id) == null){
			logger.info("Cliente no encontrado");
			return; // Salir del método si no existe el cliente
		}
		
		// Capturar nuevos datos del cliente
		logger.info("Ingrese el nuevo nombre del cliente: ");
		String nombre = consola.nextLine();
		logger.info("Ingrese el nuevo apellido del cliente: ");
		String apellido = consola.nextLine();
		logger.info("Ingrese el nuevo numero de membresia: ");
		int membresia = Integer.parseInt(consola.nextLine());
		
		// Actualizar cliente (el ID existente indica que es una actualización)
		clienteServicio.guardarCliente(new Cliente(id, nombre, apellido, membresia));
		logger.info("Cliente modificado correctamente" + nl);
		logger.info("Cliente modificado: " + clienteServicio.buscarClientePorId(id) + nl);
	}

	/**
	 * Elimina un cliente del sistema
	 * @param consola Scanner para capturar el ID del cliente a eliminar
	 */
	private void eliminarCliente(Scanner consola){
		logger.info(nl + "--- Eliminar Cliente ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		
		Cliente cliente = clienteServicio.buscarClientePorId(id);
		
		// Verificar que el cliente existe antes de eliminar
		if (cliente == null){
			logger.info("Cliente no encontrado");
			return; // Salir del método si no existe el cliente
		}
		
		// Eliminar cliente y mostrar confirmación
		clienteServicio.eliminarClientePorId(cliente);
		logger.info("Cliente eliminado correctamente" + nl);
		logger.info("Cliente eliminado: " + cliente);
	}
}