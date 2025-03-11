package com.api.bazar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Long stock;
    
    @ManyToMany(mappedBy = "ListProducts")
    private Set<Sale> sales=new HashSet<>();
    
    public Product() {
    }

    public Product(String name, String brand, BigDecimal cost, Long stock) {
        this.name = name;
        this.brand = brand;
        this.cost = cost;
        this.stock = stock;
    } 

    @Override
    public String toString() {
        return "Product{" + "idProduct=" + idProduct + ", name=" + name + ", brand=" + brand + ", cost=" + cost + ", stock=" + stock + ", sales=" + sales + '}';
    }
}
