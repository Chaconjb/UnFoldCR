package com.UnFold.service.impl; // O com.UnFold.service

import com.UnFold.domain.Factura;
import com.UnFold.repository.FacturaRepository; // Asegúrate de tener esta interfaz
import com.UnFold.service.FacturaService;     // Asegúrate de tener esta interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServices1 implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> getFacturas(boolean activas) {
        // Similar a Venta, el concepto de "activo" para una Factura podría no aplicar
        // o requerir una lógica personalizada (ej. "estado" diferente de "cancelada").
        List<Factura> facturas = facturaRepository.findAll();
        // if (activas) {
        //     facturas.removeIf(f -> !"pagada".equalsIgnoreCase(f.getEstado())); // Ejemplo: solo facturas pagadas
        // }
        return facturas;
    }

    @Override
    @Transactional(readOnly = true)
    public Factura getFactura(Factura factura) {
        return facturaRepository.findById(factura.getIdFactura()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Factura factura) {
        facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public void delete(Factura factura) {
        facturaRepository.delete(factura);
    }
}