
package com.api.bazar.controller;

import com.api.bazar.entity.Product;
import com.api.bazar.entity.dto.ProductDto;
import com.api.bazar.service.ProductService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public String createProduct(@RequestBody ProductDto product){
        ProductDto response = productService.createProduct(product);
        if (response == null){
            return "No se pudo crear el producto";
        }
        return "Se creo exitosamente el product "+ response.toString();
    }
    
    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<ProductDto> response = productService.getAllProducts();
        if (response.isEmpty()){
            return null;
        }
        return response;
    }
    
    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id){
        ProductDto response = productService.getProduct(id);
        if(response == null){
            return "Producto no  encontrado: " + id;
        }
        return "Producto encontrado " + response;
    }
    
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
       String response = productService.deleteProduct(id);
       return response;
    }
    
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id ,@RequestBody ProductDto request){
        if(request.getIdProduct() == null || !id.equals(request.getIdProduct())){
            return "Credenciales incorrectas";
        }
        ProductDto response =productService.updateProduct(request);
        if (response == null){
            return "No se encontro el producto";
        }
        return "Se actualizo con exito: " + response.toString();
    }
    
}
