
package com.api.bazar.service;

import com.api.bazar.entity.Product;
import com.api.bazar.entity.dto.ProductDto;
import com.api.bazar.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public ProductDto createProduct(ProductDto request){
        Product product = new Product();
        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setCost(request.getCost());
        product.setStock(request.getStock());
        Product aux = productRepository.save(product);
        if (aux == null){
            return null;
        }
        ProductDto response = new ProductDto();
        response.setIdProduct(aux.getIdProduct());
        response.setName(aux.getName());
        response.setBrand(aux.getBrand());
        response.setCost(aux.getCost());
        response.setStock(aux.getStock());
        return response;
    }
    
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> response = products.stream().map((product)->{
            ProductDto aux = new ProductDto();
            aux.setBrand(product.getBrand());
            aux.setIdProduct(product.getIdProduct());
            aux.setName(product.getName());
            aux.setCost(product.getCost());
            aux.setStock(product.getStock());
            return aux;
        
        }).collect(Collectors.toList());
        return response;
    }
    
    public ProductDto getProduct(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            return null;
        }
        ProductDto response = new ProductDto();
        response.setIdProduct(product.get().getIdProduct());
        response.setName(product.get().getName());
        response.setBrand(product.get().getBrand());
        response.setCost(product.get().getCost());
        response.setStock(product.get().getStock());
        return response;
    }
    
    public String deleteProduct(Long id){
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return "Se elimino con exito el producto";
        }
        return "El producto que quiere eliminar no existe: " + id;
    }
    
    public ProductDto updateProduct(ProductDto request){
        if(!productRepository.existsById(request.getIdProduct())){
            return null;
        }
        ProductDto response = createProduct(request);
        if(response == null){
            return null;
        }
        return response;
    }
    
}
