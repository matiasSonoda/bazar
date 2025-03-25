
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
import com.api.bazar.exception.EmptyStockException;
import com.api.bazar.exception.ProductNotFoundException;
import com.api.bazar.exception.SaleNotFoundException;
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
        //Declaracion de las variables
        Sale sale = new Sale();
        List<Product> products = new ArrayList<>();
        List<ProductSale> listProdSale = new ArrayList<>();
        //Valida si el dto ya tiene id, eso significa que viene por parte de una solicitud PUT
        if(saleDto.getIdSale() != null){
            sale.setIdSale(saleDto.getIdSale());
        }
        //Busco el cliente y si existe lo guardo en un objeto
        
        Customer customer = customerRepository.findById(saleDto.getCustomer().getIdCustomer())
                .orElseThrow(() -> new CustomerNotFoundException("no se encontro el cliente"));
        //Agrego el cliente a la instancia de Sale
        sale.setCustomer(customer);
        //Agrego la fecha de la venta en la instancia de Sale
        sale.setDateSale(saleDto.getDateSale());
        
        //Agrego los productos del DTO a la lista de products si existen en la base de datos        
        products = saleDto.getProducts().stream().map(e ->{
            Product prod = productRepository.findById(e.getIdProduct())
                    .orElseThrow(() -> new ProductNotFoundException("producto no encontrado" + e.getIdProduct()));
                    //Actualizo el stock en la bdd restandolo por cada producto que tenga la lista
                    if (prod.getStock() < 0){
                        throw new EmptyStockException("No hay suficiente stock para vender el producto: " + prod.getIdProduct());
                    }
                    prod.setStock(prod.getStock() - e.getQuantity());
                   
                    productRepository.save(prod);
                    return prod;})
                    .collect(Collectors.toList());

        
        //Calculo el valor total de la venta
        BigDecimal totalPrice = products.stream()
                .map(product -> {
                    ProductDto prodDto = saleDto.getProducts().stream()
                            .filter(dto -> dto.getIdProduct().equals(product.getIdProduct()))
                            .findFirst()
                            .orElseThrow(()-> new ProductNotFoundException("No se encontro el producto para cacular el valor total: " + product.getIdProduct()));
                
                return product.getCost().multiply(new BigDecimal(prodDto.getQuantity()));})
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        sale.setTotal(totalPrice);
        
        Sale actualSale = saleRepository.save(sale);
       
        products.stream().forEach((prod) -> {ProductSale prodSale = new ProductSale();
                                             ProductSaleId productSaleId = new ProductSaleId();
                                             productSaleId.setProductId(prod.getIdProduct());
                                             productSaleId.setSaleId(actualSale.getIdSale());
                                             prodSale.setIdProductSale(productSaleId);
                                             prodSale.setProduct(prod);
                                             prodSale.setSale(actualSale);
                                             saleDto.getProducts().stream().filter(dto -> dto.getIdProduct().equals(prod.getIdProduct()))
                                                    .findFirst()
                                                    .ifPresent(dto -> {
                                                        if (dto.getQuantity() == null || dto.getQuantity() < 0){
                                                            throw new IllegalArgumentException("Valores invalidos para la cantidad del producto: " + dto.getIdProduct());
                                                        }
                                                    prodSale.setQuantity(dto.getQuantity());}
                                            );
                                            listProdSale.add(productSaleRepository.save(prodSale));
                                            });
        actualSale.setProducts(listProdSale);
        return saleRepository.save(actualSale);
    }
    
    public List<SaleDto> getAllSales(){
        List<Sale> sales = saleRepository.findAll();
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
        if(!saleRepository.existsById(id)){
            throw new SaleNotFoundException("No se encontro la venta solicitada: " + id);
        }
        Optional<Sale> sale = saleRepository.findById(id);
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
        
    public void deleteSale(Long id){
        if (!saleRepository.existsById(id)){
            throw new SaleNotFoundException("No se encontro la venta para borrarla: " + id);
        }
        saleRepository.deleteById(id);
    }
    
    public Sale updateSale(Long id, SaleDto saleDto){
        if(!saleRepository.existsById(saleDto.getIdSale())){
            throw new SaleNotFoundException("No se encontro la venta para actualizarla: " + saleDto.getIdSale());
        }
        if (saleDto.getIdSale() == null || !id.equals(saleDto.getIdSale())){
            throw new IllegalArgumentException("Credenciales inalidas para actualizar la venta");
        }
        return saveSale(saleDto);
    }
}
