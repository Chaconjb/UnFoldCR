package com.UnFold.repository;
import com.UnFold.domain.Factura;
import com.UnFold.domain.Venta;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Factura findByIdFactura(Long idFactura);
    
    List<Venta> findByFechaBetween(Date fechaInicial, Date fechaFinal);

    // Buscar ventas en un rango de fechas y por cliente
    List<Venta> findByFechaBetweenAndIdCliente(Date fechaInicial, Date fechaFinal, Long idCliente);
    
}