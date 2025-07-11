package com.UnFold.service;

import com.UnFold.domain.Factura;
import java.util.List;

public interface FacturaService {
    
    public List<Factura> getFacturas(boolean activos);

    public Factura getFactura(Factura factura);

    public void save(Factura factura);

    public void delete(Factura factura);
    
}
