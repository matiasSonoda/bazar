
package com.api.bazar.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDto {
    
    private Long idProduct;
    @Size(min = 2 , max = 20, message = "The name must have 2 min characters and 20 max characters")
    @NotBlank(message="Insert the name of the product")
    private String name;
    @Size(min = 2, max = 20, message = "The brand must have 2 min characters and 20 max characters")
    @NotBlank(message = "Inser the name of the brand")
    private String brand;
    @Positive(message="The cost must be positive")
    @NotNull @Min(0)
    private BigDecimal cost;
    @Positive(message="The stock must be positive")
    @NotNull @Min(0)
    private Long stock;
    @Positive(message="The quantity must be positive")
    @NotNull @Min(0)
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
