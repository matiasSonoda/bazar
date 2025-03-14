
package com.api.bazar.entity.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDto {
    
    private Long idProduct;
    private String name;
    private String brand;
    private BigDecimal cost;
    private Long quantity;

    public ProductDto() {
    }

    public ProductDto(Long idProduct, String name, String brand, BigDecimal cost, Long quantity) {
        this.idProduct = idProduct;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
        this.quantity = quantity;
    }
    
    
    
    
}
