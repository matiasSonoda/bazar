package com.api.bazar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "name", nullable = false)
    @NotNull @NotBlank(message = "Insert a name")
    private String name;
    @Column(name = "brand", nullable = false)
    @NotNull @NotBlank(message = "Insert a brand")
    private String brand;
    @Column(name = "cost", nullable = false)
    @NotNull @Min(0)
    private BigDecimal cost;
    @Column(name = "stock")
    @NotNull @Min(0)
    private Double stock;

    public Product() {
    }

    public Product(String name, String brand, BigDecimal cost, Double stock) {
        this.name = name;
        this.brand = brand;
        this.cost = cost;
        this.stock = stock;
    } 
}
