
package com.api.bazar.entity.dto;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDto {
    
    private Long idSale;
    @NotNull @PastOrPresent
    private LocalDateTime dateSale = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    @NotNull @Min(0) @Positive(message = "The total must be positive")
    private BigDecimal total;
    private List<ProductDto> products = new ArrayList<>();
    private CustomerDto customer;
    
    public SaleDto() {
    }

    public SaleDto(Long idSale, LocalDateTime dateSale, BigDecimal total, CustomerDto customer) {
        this.idSale = idSale;
        this.dateSale = dateSale;
        this.total = total;
        this.customer = customer;
    }

   

    @Override
    public String toString() {
        return "SaleDto{" + "idSale=" + idSale + ", dateSale=" + dateSale + ", total=" + total + ", listProducts=" + products + ", customer=" + customer + '}';
    }   

    
    
}
