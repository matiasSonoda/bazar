
package com.api.bazar.controller;

import com.api.bazar.entity.Product;
import com.api.bazar.entity.Sale;
import com.api.bazar.entity.dto.SaleDto;
import com.api.bazar.service.SaleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sale")
public class SaleController {
    
    @Autowired
    private SaleService saleService;
    
    @PostMapping
    public String saveSale(@RequestBody SaleDto saleDto){
        Sale sale = saleService.saveSale(saleDto);
        
        if (sale == null){
            return "No se pudo crear la venta";
        }
        return "Se genero con exito la venta: " + sale.toString();
  
    }
    
    @GetMapping
    public List<Sale> getAllSales(){
        return saleService.getAllSales();
    }
    
    @GetMapping("/{id}")
    public Optional<Sale> getSale(@PathVariable Long id) {
        return saleService.getSale(id);
    }
    
    @DeleteMapping("/{id}")
    public String deleteSale(@PathVariable Long id){
        saleService.deleteSale(id);
        return "Se elimino con exito la venta";
    }
    
    @PutMapping("/{id}")
    public String updateSale(@PathVariable Long id, @RequestBody SaleDto saleDto){
        if (saleDto.getIdSale() == null || !id.equals(saleDto.getIdSale())){
            return "Credenciales incorrectas";
        }
        Sale newSale = saleService.updateSale(saleDto);
        if ( newSale == null){
            return "Venta no encontrada";
        }
        return "Venta actualizada con exito: " + newSale.toString();
    }
}
