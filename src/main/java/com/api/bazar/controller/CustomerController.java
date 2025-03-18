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
        CustomerDto response = customerService.saveCustomer(customer);
        if(response == null){
            return "No se pudo crear el cliente";
        }
        return "Se guardo con exito el nuevo customer: " + response.toString();
    }
    
    @GetMapping
    public List<CustomerDto> getAllCustomer(){
        return customerService.getAllCustomer();
    }
    
    @GetMapping("/{id}")
    public String getCustomer(@PathVariable Long id){
        CustomerDto response = customerService.getCustomer(id);
        if(response == null){
            return "No se encontro el cliente con el id: " + id;
        }
        return "Cliente encontrado: " + response.toString();
    }
    
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }
    
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody CustomerDto request){
        if(request.getIdCustomer() == null || !id.equals(request.getIdCustomer())){
            return "Credenciales incorrectas";
        }
        CustomerDto response =  customerService.updateCustomer(id, request);
        if (response != null){
            return "Se actualizo con exito el customer: " + response.toString();
        }
        return "Customer inexistente";
    }
}
