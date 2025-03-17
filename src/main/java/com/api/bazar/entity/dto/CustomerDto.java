package com.api.bazar.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerDto {
    
    private Long idCustomer;
    private String name;
    private String lastName;
    private String dni;

    public CustomerDto() {
    }

    public CustomerDto(Long idCustomer, String name, String lastName, String dni) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "CustomerDto{" + "idCustomer=" + idCustomer + ", name=" + name + ", lastName=" + lastName + ", dni=" + dni + '}';
    }
    
    
}
