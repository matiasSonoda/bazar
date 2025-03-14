
package com.api.bazar.service;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.Product;
import com.api.bazar.entity.Sale;
import com.api.bazar.entity.dto.SaleDto;
import com.api.bazar.repository.CustomerRepository;
import com.api.bazar.repository.ProductRepository;
import com.api.bazar.repository.SaleRepository;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    public Sale saveSale(SaleDto saleDto){
        //Inicio las variables
        Sale sale = new Sale();
        List<Product> products = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        //Busco el cliente y si existe lo guardo en un objeto
        Customer customer = customerRepository.findById(saleDto.getCustomer().getIdCustomer())
                .orElseThrow(() -> new RuntimeException("no se encontro el cliente"));
        //Agrego el cliente a la instnacia de Sale
        sale.setCustomer(customer);
        //Agrego la fecha de la venta en la instancia de Sale
        sale.setDateSale(saleDto.getDateSale());
        
        //Agrego los productos del DTO a la lista de products si existen en la base de datos
        products = saleDto.getProducts().stream().map(e ->
            productRepository.findById(e.getIdProduct())
                    .orElseThrow(() -> new RuntimeException("producto no encontrado" + e.getIdProduct())))
                    .collect(Collectors.toList());  
        sale.setProducts(products);
        
        //Actualizo el stock en la bdd restandolo 1 en 1 por cada producto que tenga la lista

        products.stream().forEach(e -> {
            Long stock = e.getStock() - 1;
            if(stock < 0){
                new RuntimeException("no hay stock de " + e.getName());
            }
            e.setStock(stock);
            productRepository.save(e);
        });
        
        //Calculo el valor total de la venta
        saleDto.getProducts().forEach(e->{
            total.add((e.getCost().multiply(new BigDecimal(e.getQuantity()))), MathContext.UNLIMITED);  
        });
        System.out.println(total);
        sale.setTotal(total);
        
       return saleRepository.save(sale);
    }
    
    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }
    
    public Optional<Sale> getSale(Long id){
        return saleRepository.findById(id);
    }
    
    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }
    
    public Sale updateSale(SaleDto saleDto){
        if(!saleRepository.existsById(saleDto.getIdSale())){
            return null;
        }
        return saveSale(saleDto);
    }
}
