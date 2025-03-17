package com.api.bazar.controller;

import com.api.bazar.entity.Customer;
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
    public String saveCustomer(@RequestBody Customer customer){
        Customer custom = customerService.saveCustomer(customer);
        return "Se guardo con exito el nuevo customer: " + custom.toString();
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
    public String updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        if(customer.getIdCustomer() == null || !id.equals(customer.getIdCustomer())){
            return "Credenciales incorrectas";
        }
        Customer newCustom =  customerService.updateCustomer(id, customer);
        
        if (newCustom != null){
            return "Se actualizo con exito el customer: " + newCustom.toString();
        }
        return "Customer inexistente";
    }
}
