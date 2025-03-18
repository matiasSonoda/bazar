
package com.api.bazar.controller;

import com.api.bazar.entity.Product;
import com.api.bazar.entity.Sale;
import com.api.bazar.entity.dto.SaleDto;
import com.api.bazar.entity.dto.CustomerDto;
import com.api.bazar.entity.dto.ProductDto;
import com.api.bazar.service.SaleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
        
        CustomerDto customerDto = new CustomerDto();
        customerDto.setDni(sale.getCustomer().getDni());
        customerDto.setIdCustomer(sale.getCustomer().getIdCustomer());
        customerDto.setName(sale.getCustomer().getName());
        customerDto.setLastName(sale.getCustomer().getLastName());
        
        SaleDto response = new SaleDto();
        response.setCustomer(customerDto);
        response.setIdSale(sale.getIdSale());
        response.setDateSale(sale.getDateSale());
        response.setTotal(sale.getTotal());
        
        sale.getProducts().stream().forEach((product)->{
            ProductDto productDto = new ProductDto();
            productDto.setBrand(product.getProduct().getBrand());
            productDto.setCost(product.getProduct().getCost());
            productDto.setIdProduct(product.getProduct().getIdProduct());
            productDto.setName(product.getProduct().getName());
            productDto.setQuantity(product.getQuantity());        
            
            response.getProducts().add(productDto);
        });
        
        return "Se genero con exito la venta: " + response.toString();
  
    }
    
    @GetMapping
    public List<SaleDto> getAllSales(){
        List<SaleDto> response = saleService.getAllSales();
        if(response.isEmpty()){
            return null;
        }
        return response;
    }
    
    @GetMapping("/{id}")
    public SaleDto getSale(@PathVariable Long id) {
        SaleDto response = saleService.getSale(id);
        if (response == null){
            return null;
        }
        return response;
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
