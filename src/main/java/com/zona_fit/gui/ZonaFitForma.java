package com.zona_fit.gui;

import com.zona_fit.modelo.Cliente;
import com.zona_fit.servicio.ClienteServicio;
import com.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"ID", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);
        //Cargar listado de clientes
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente(){
        if (nombreTexto.getText().equals("")){
            mostrarMensaje("Proporcione un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if (membresiaTexto.getText().equals("")){
            mostrarMensaje("Proporciona una membresia");
            membresiaTexto.requestFocusInWindow();
            return;
        }
        //Recuperamos los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        //Creamos el nuevo cliente
        var cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        //Guardamos el cliente
        this.clienteServicio.guardarCliente(cliente);// inserta en la base de datos
        mostrarMensaje("Cliente guardado con exito");
        limpiarFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon != -1){// -1 significa que no selecciono ningun registro
            String id = clientesTabla.getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);

        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
    }
}
