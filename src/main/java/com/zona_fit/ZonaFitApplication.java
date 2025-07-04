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


@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando aplicacion");
		//Levantar la fabrica de Spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicacion finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		logger.info(nl + "*** Aplicacion ZonaFit ***" + nl);
		boolean salir = false;
		Scanner consola = new Scanner(System.in);
		while (!salir) {
			int opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}


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

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		boolean salir = false;
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

	private void listarClientes(){
		logger.info(nl + "--- Listando Clientes ---" + nl);
		List<Cliente> clientes = clienteServicio.listarClientes();
		clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
	}

	private void agregarCliente(Scanner consola){
		logger.info(nl + "--- Agregar Cliente ---" + nl);
		logger.info("Ingrese el nombre del cliente: ");
		String nombre = consola.nextLine();
		logger.info("Ingrese el apellido del cliente: ");
		String apellido = consola.nextLine();
		logger.info("Ingrese numero de membresia: ");
		int membresia = Integer.parseInt(consola.nextLine());
		clienteServicio.guardarCliente(new Cliente(null, nombre, apellido, membresia));
		logger.info("Cliente agregado correctamente");
	}

	private void buscarClientePorId(Scanner consola){
		logger.info(nl + "--- Buscar Cliente por ID ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		Cliente cliente = clienteServicio.buscarClientePorId(id);
		if (cliente != null){
			logger.info("Cliente encontrado: " + cliente + nl);
		} else {
			logger.info("Cliente no encontrado: " + cliente + nl);
		}
	}

	private void modificarCliente(Scanner consola){
		logger.info(nl + "--- Modificar Cliente ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		if (clienteServicio.buscarClientePorId(id) == null){
			logger.info("Cliente no encontrado");
			return;
		}
		logger.info("Ingrese el nuevo nombre del cliente: ");
		String nombre = consola.nextLine();
		logger.info("Ingrese el nuevo apellido del cliente: ");
		String apellido = consola.nextLine();
		logger.info("Ingrese el nuevo numero de membresia: ");
		int membresia = Integer.parseInt(consola.nextLine());
		clienteServicio.guardarCliente(new Cliente(id, nombre, apellido, membresia));
		logger.info("Cliente modificado correctamente" + nl);
		logger.info("Cliente modificado: " + clienteServicio.buscarClientePorId(id) + nl);
	}

	private void eliminarCliente(Scanner consola){
		logger.info(nl + "--- Eliminar Cliente ---" + nl);
		logger.info("Ingrese el ID del cliente: ");
		int id = Integer.parseInt(consola.nextLine());
		Cliente cliente  = clienteServicio.buscarClientePorId(id);
		if (cliente == null){
			logger.info("Cliente no encontrado");
			return;
		}
		clienteServicio.eliminarClientePorId(cliente);
		logger.info("Cliente eliminado correctamente" + nl);
		logger.info("Cliente eliminado: " + cliente);
	}

}
