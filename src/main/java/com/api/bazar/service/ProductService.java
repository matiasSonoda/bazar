
package com.api.bazar.service;

import com.api.bazar.entity.Product;
import com.api.bazar.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    
    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }
    
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    
    public Product updateProduct(Product product){
        if(!productRepository.existsById(product.getIdProduct())){
            return null;
        }
        return createProduct(product);
  
    }
    
}
