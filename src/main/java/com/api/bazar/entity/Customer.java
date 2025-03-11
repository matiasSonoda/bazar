package com.api.bazar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "client")
@Setter @Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    @NotNull @NotBlank(message = "Insert a name")
    @Column(name = "name", nullable = false)
    private String name;
    @NotNull @NotBlank(message = "Insert a last name")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NotNull @NotBlank(message = "Insert a DNI")
    @Column(name = "dni", nullable = false)
    private String dni;

    public Customer(String name, String lastName, String dni) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
    }

    public Customer() {
    }
    
}
