package com.api.bazar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="sale")
@Getter @Setter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSale;
    @NotNull @PastOrPresent
    private LocalDateTime dateSale;
    @NotNull @Min(0)
    private BigDecimal total;
    @NotNull
    private List<Product> ListProducts;
    @NotNull
    private Customer customer;

    public Sale() {
    }

    public Sale(LocalDateTime dateSale, BigDecimal total, List<Product> ListProducts, Customer customer) {
        this.dateSale = dateSale;
        this.total = total;
        this.ListProducts = ListProducts;
        this.customer = customer;
    }
    
    
}
