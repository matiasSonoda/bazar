
package com.api.bazar.controller;

import com.api.bazar.entity.Product;
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
    public String createProduct(@RequestBody @Valid Product product){
        Product prod = productService.createProduct(product);
        
        return "Se creo exitosamente el product "+ prod.toString();
    }
    
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }
    
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
       productService.deleteProduct(id);
       return "Producto eliminado con exito";
    }
    
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id ,@RequestBody Product product){
        if(product.getIdProduct() == null || !id.equals(product.getIdProduct())){
            return "Credenciales incorrectas";
        }
        Product prod =productService.updateProduct(product);
        if (prod == null){
            return "No se encontro el producto";
        }
        return "Se actualizo con exito: " + prod.toString();
    }
    
}
