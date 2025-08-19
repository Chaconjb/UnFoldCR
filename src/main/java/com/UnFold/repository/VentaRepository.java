package com.UnFold.repository;
import com.UnFold.domain.Venta;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaBetween(Date fechaInicial, Date fechaFinal);
    List<Venta> findByFechaBetweenAndIdCliente(Date fechaInicial, Date fechaFinal, Long idCliente);
}