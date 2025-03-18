package com.api.bazar.controller;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.dto.CustomerDto;
import com.api.bazar.service.CustomerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public String saveCustomer(@RequestBody CustomerDto customer){
        Customer request = customerService.saveCustomer(customer);
        if(request == null){
            return "No se pudo crear el cliente";
        }
        CustomerDto response = new CustomerDto();
        response.setDni(request.getDni());
        response.setIdCustomer(request.getIdCustomer());
        response.setLastName(request.getLastName());
        response.setName(request.getName());
        
        return "Se guardo con exito el nuevo customer: " + response.toString();
    }
    
    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }
    
    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }
    
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "Customer eliminado con exito";
    }
    
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody CustomerDto request){
        if(request.getIdCustomer() == null || !id.equals(request.getIdCustomer())){
            return "Credenciales incorrectas";
        }
        Customer auxiliar =  customerService.updateCustomer(id, request);
        
        if (auxiliar != null){
            CustomerDto response = new CustomerDto();
            response.setIdCustomer(auxiliar.getIdCustomer());
            response.setDni(auxiliar.getDni());
            response.setName(auxiliar.getName());
            response.setLastName(auxiliar.getLastName());
            return "Se actualizo con exito el customer: " + response.toString();
        }
        return "Customer inexistente";
    }
}
