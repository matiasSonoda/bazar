
package com.api.bazar.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSaleDto {
    
    private ProductSaleIdDto idProductSale;

    private ProductDto product;
    
    private SaleDto sale;
    @Positive(message = "the quantity must be positive")
    @NotNull @Min(0)
    private Integer quantity;

    public ProductSaleDto() {
    }

    @Override
    public String toString() {
        return "ProductSaleDto{" + "idProductSale=" + idProductSale + ", product=" + product + ", sale=" + sale + ", quantity=" + quantity + '}';
    }

    
    
}
