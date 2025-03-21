
package com.api.bazar.service;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.Product;
import com.api.bazar.entity.ProductSale;
import com.api.bazar.entity.ProductSaleId;
import com.api.bazar.entity.Sale;
import com.api.bazar.entity.dto.CustomerDto;
import com.api.bazar.entity.dto.ProductDto;
import com.api.bazar.entity.dto.SaleDto;
import com.api.bazar.exception.CustomerNotFoundException;
import com.api.bazar.repository.CustomerRepository;
import com.api.bazar.repository.ProductRepository;
import com.api.bazar.repository.ProductSaleRepository;
import com.api.bazar.repository.SaleRepository;
import jakarta.transaction.Transactional;
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
    @Autowired
    private ProductSaleRepository productSaleRepository;
    
    @Transactional
    public Sale saveSale(SaleDto saleDto){
        System.out.println("SOY EL TOSTRING DEL SALEDTO:     " + saleDto.toString());
        //Inicio las variables
        Sale sale = new Sale();
        List<Product> products = new ArrayList<>();
        List<ProductSale> listProdSale = new ArrayList<>();
        //Busco el cliente y si existe lo guardo en un objeto
        Customer customer = customerRepository.findById(saleDto.getCustomer().getIdCustomer())
                .orElseThrow(() -> new CustomerNotFoundException("no se encontro el cliente"));
        System.out.println("Soy el cliente encontrado:        " + customer.toString());
        //Agrego el cliente a la instnacia de Sale
        sale.setCustomer(customer);
        //Agrego la fecha de la venta en la instancia de Sale
        sale.setDateSale(saleDto.getDateSale());
        
        //Agrego los productos del DTO a la lista de products si existen en la base de datos
        saleDto.getProducts().stream().forEach(e -> System.out.println("SOY getProducts de saleDTO !!!! :::::   " + e.toString()));
        
        products = saleDto.getProducts().stream().map(e ->
            productRepository.findById(e.getIdProduct())
                    .orElseThrow(() -> new RuntimeException("producto no encontrado" + e.getIdProduct())))
                    .collect(Collectors.toList());
        System.out.println("HOLAAAAA        " + products.get(0).getName());
        products.stream().forEach(e -> System.out.println("AAAAAAAAAAAAA   Soy la lista de productos comprados:         " + e.toString()));
        
        //Actualizo el stock en la bdd restandolo por cada producto que tenga la lista
        products.stream().forEach(product -> {
            ProductDto productDto = saleDto.getProducts().stream()
                .filter(dto -> dto.getIdProduct().equals(product.getIdProduct()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el producto en la venta: " + product.getIdProduct()));

            // Resta la cantidad del stock
            Long stock = product.getStock() - productDto.getQuantity();
            if (stock < 0) {
                throw new RuntimeException("No hay suficiente stock para el producto: " + product.getName());
            }
            product.setStock(stock); // Actualiza el stock
            productRepository.save(product); // Guarda los cambios en la base de datos
        });

        
        //Calculo el valor total de la venta
        BigDecimal totalPrice = products.stream()
                .map(product -> {
                    ProductDto prodDto = saleDto.getProducts().stream()
                            .filter(dto -> dto.getIdProduct().equals(product.getIdProduct()))
                            .findFirst()
                            .orElseThrow(()-> new RuntimeException("No se encontro el producto para cacular el valor total: " + product.getIdProduct()));
                
                return product.getCost().multiply(new BigDecimal(prodDto.getQuantity()));})
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        System.out.println(totalPrice);
        sale.setTotal(totalPrice);
        
       Sale actualSale = saleRepository.save(sale);
       
       products.stream().forEach((prod) -> {ProductSale prodSale = new ProductSale();
                                            ProductSaleId productSaleId = new ProductSaleId();
                                            productSaleId.setProductId(prod.getIdProduct());
                                            productSaleId.setSaleId(actualSale.getIdSale());
                                             prodSale.setIdProductSale(productSaleId);
                                             System.out.println("ACAAAAAA AHORAAA ACAAA !!!    " +prod);
                                             prodSale.setProduct(prod);
                                             prodSale.setSale(actualSale);
                                             saleDto.getProducts().stream().forEach((dto)-> {
                                                if(dto.getIdProduct().equals(prod.getIdProduct()))
                                                    prodSale.setQuantity(dto.getQuantity());
                                                });
                                            listProdSale.add(productSaleRepository.save(prodSale));
                                            });
        actualSale.setProducts(listProdSale);
        return saleRepository.save(actualSale);
    }
    
    public List<SaleDto> getAllSales(){
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()){
            return null;
        }
        List<SaleDto> response = sales.stream().map((sale)->{
            SaleDto aux = new SaleDto();
            
            CustomerDto customer = new CustomerDto();
            customer.setIdCustomer(sale.getCustomer().getIdCustomer());
            customer.setDni(sale.getCustomer().getDni());
            customer.setName(sale.getCustomer().getName());
            customer.setLastName(sale.getCustomer().getLastName());
            
            List<ProductDto> products = sale.getProducts().stream().map((product)->{
                ProductDto dto = new ProductDto();
                dto.setIdProduct(product.getProduct().getIdProduct());
                dto.setName(product.getProduct().getName());
                dto.setCost(product.getProduct().getCost());
                dto.setBrand(product.getProduct().getBrand());
                dto.setQuantity(product.getQuantity());
                return dto;
            }).collect(Collectors.toList());
            
            aux.setIdSale(sale.getIdSale());
            aux.setCustomer(customer);
            aux.setDateSale(sale.getDateSale());
            aux.setProducts(products);
            aux.setTotal(sale.getTotal());
            return aux;
        }).collect(Collectors.toList());
    
        return response;
    }
    
    public SaleDto getSale(Long id){
        if(saleRepository.existsById(id)){
            Optional<Sale> sale = saleRepository.findById(id);
            if(sale.isEmpty()){
                return null;
            }
            SaleDto response = new SaleDto();

            CustomerDto customer = new CustomerDto();
            customer.setIdCustomer(sale.get().getCustomer().getIdCustomer());
            customer.setDni(sale.get().getCustomer().getDni());
            customer.setName(sale.get().getCustomer().getName());
            customer.setLastName(sale.get().getCustomer().getLastName());

            List<ProductDto> products = sale.get().getProducts().stream().map((product)->{
                    ProductDto dto = new ProductDto();
                    dto.setIdProduct(product.getProduct().getIdProduct());
                    dto.setName(product.getProduct().getName());
                    dto.setCost(product.getProduct().getCost());
                    dto.setBrand(product.getProduct().getBrand());
                    dto.setQuantity(product.getQuantity());
                    return dto;
                }).collect(Collectors.toList());

            response.setIdSale(sale.get().getIdSale());
            response.setCustomer(customer);
            response.setDateSale(sale.get().getDateSale());
            response.setProducts(products);
            response.setTotal(sale.get().getTotal());
            
            return response;
                
        }
        return null;
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
