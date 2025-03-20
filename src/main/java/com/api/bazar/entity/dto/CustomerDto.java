package com.api.bazar.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerDto {
    
    private Long idCustomer;
    @NotBlank(message = "Insert your name")
    @Size(min = 2 , max = 20, message = "The name must have between 2 min characters and 20 max characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name only can contain letters and space")
    private String name;
    @NotBlank(message = "Insert your last name")
    @Size(min = 2 , max = 20, message = "The last name must have between 2 min characters and 20 max characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The last name only can contain letters and space")
    private String lastName;
    @NotBlank(message = "Insert your DNI")
    @Pattern(regexp = "\\d{8}", message = "The DNI must be a numeric type and must have 8 characters")
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
