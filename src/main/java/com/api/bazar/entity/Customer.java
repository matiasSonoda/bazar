package com.api.bazar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "client")
@Setter @Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;
    @NotNull @NotBlank(message = "Insert a name")
    @Column(name = "name", nullable = false)
    private String name;
    @NotNull @NotBlank(message = "Insert a last name")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NotNull @NotBlank(message = "Insert a DNI")
    @Column(name = "dni", nullable = false)
    private String dni;
    
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales = new HashSet<>();

    public Customer(String name, String lastName, String dni) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" + "idCustomer=" + idCustomer + ", name=" + name + ", lastName=" + lastName + ", dni=" + dni + ", sales=" + sales + '}';
    }
}
