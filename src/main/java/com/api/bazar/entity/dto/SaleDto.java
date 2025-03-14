
package com.api.bazar.entity.dto;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDto {
    
    private Long idSale;
    private LocalDateTime dateSale;
    private BigDecimal total;
    private List<Product> listProducts = new ArrayList<>();
    private Customer customer;
    
    public SaleDto() {
    }

    public SaleDto(Long idSale, LocalDateTime dateSale, BigDecimal total, Customer customer) {
        this.idSale = idSale;
        this.dateSale = dateSale;
        this.total = total;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "SaleDto{" + "idSale=" + idSale + ", dateSale=" + dateSale + ", total=" + total + ", listProducts=" + listProducts + ", customer=" + customer + '}';
    }   

    
    
}
