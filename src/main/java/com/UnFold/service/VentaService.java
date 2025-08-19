package com.UnFold.service;

import com.UnFold.domain.Venta;
import com.UnFold.repository.VentaRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    public List<Venta> getVentasPorFecha(Date fechaInicial, Date fechaFinal) {
        return ventaRepository.findByFechaBetween(fechaInicial, fechaFinal);
    }

    public List<Venta> getVentasPorFechaYCliente(Date fechaInicial, Date fechaFinal, Long idCliente) {
        return ventaRepository.findByFechaBetweenAndIdCliente(fechaInicial, fechaFinal, idCliente);
    }
}