
package com.api.bazar.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSaleDto {
    
    private ProductSaleIdDto idProductSale;

    private ProductDto product;
    

    private SaleDto sale;
    
    private Integer quantity;

    public ProductSaleDto() {
    }

    @Override
    public String toString() {
        return "ProductSaleDto{" + "idProductSale=" + idProductSale + ", product=" + product + ", sale=" + sale + ", quantity=" + quantity + '}';
    }

    
    
}
