/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UnFold.service;
import com.UnFold.domain.Venta;
import java.util.List;
/**
 *
 * @author campo
 */
public interface VentaService {
    
    public List<Venta> getVentas(boolean activos);

    public Venta getVenta(Venta venta);

    public void save(Venta venta);

    public void delete(Venta venta);
    
}
