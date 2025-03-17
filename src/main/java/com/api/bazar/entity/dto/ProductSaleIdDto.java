package com.api.bazar.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class ProductSaleIdDto {
    private Long productId;
    private Long saleId;

    public ProductSaleIdDto() {
    }

    @Override
    public String toString() {
        return "ProductSaleIdDto{" + "productId=" + productId + ", saleId=" + saleId + '}';
    }

    
    
}
