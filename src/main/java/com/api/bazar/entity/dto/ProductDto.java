
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
    private Long stock;
    private Integer quantity;

    public ProductDto() {
    }

    public ProductDto(Long idProduct, String name, String brand,Long stock, BigDecimal cost, Integer quantity) {
        this.idProduct = idProduct;
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.cost = cost;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDto{" + "idProduct=" + idProduct + ", name=" + name + ", brand=" + brand + ", cost=" + cost + ", stock=" + stock + ", quantity=" + quantity + '}';
    }

    
    
    
    
    
}
