package com.zona_fit.gui;

import com.zona_fit.servicio.ClienteServicio;
import com.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    IClienteServicio clienteServicio;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;

    }
}
