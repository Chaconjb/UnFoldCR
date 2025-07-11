package com.UnFold.service.impl; // O com.UnFold.service

import com.UnFold.domain.Venta;
import com.UnFold.repository.VentaRepository; // Asegúrate de tener esta interfaz
import com.UnFold.service.VentaService;     // Asegúrate de tener esta interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServices1 implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Venta> getVentas(boolean activos) {
        // En una venta, "activo" podría significar "vigente", "no cancelada", etc.
        // Si no tienes un campo 'activo' en Venta, puedes simplemente devolver findAll()
        // o implementar una lógica de filtro diferente.
        List<Venta> ventas = ventaRepository.findAll();
        // if (activos) {
        //     ventas.removeIf(v -> !v.isActiva()); // Si tienes un campo 'activa' en Venta
        // }
        return ventas; // O return ventaRepository.findByActiva(activos); si lo tienes en el repo
    }

    @Override
    @Transactional(readOnly = true)
    public Venta getVenta(Venta venta) {
        return ventaRepository.findById(venta.getIdVenta()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Venta venta) {
        ventaRepository.save(venta);
    }

    @Override
    @Transactional
    public void delete(Venta venta) {
        ventaRepository.delete(venta);
    }
}