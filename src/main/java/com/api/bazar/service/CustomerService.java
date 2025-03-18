
package com.api.bazar.service;

import com.api.bazar.entity.Customer;
import com.api.bazar.entity.dto.CustomerDto;
import com.api.bazar.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public CustomerDto saveCustomer(CustomerDto dto){
        Customer request = new Customer();
        request.setDni(dto.getDni());
        request.setName(dto.getName());
        request.setLastName(dto.getLastName());
        Customer aux = customerRepository.save(request);
        CustomerDto response = new CustomerDto();
        response.setIdCustomer(aux.getIdCustomer());
        response.setDni(aux.getDni());
        response.setName(aux.getName());
        response.setLastName(aux.getLastName());
        return response;
    }
    
    public List<CustomerDto> getAllCustomer(){
       List<Customer> customers = customerRepository.findAll();
       List<CustomerDto> dto = new ArrayList<>();
       
       dto = customers.stream().map((custom)->{
                CustomerDto aux = new CustomerDto();
                aux.setDni(custom.getDni());
                aux.setIdCustomer(custom.getIdCustomer());
                aux.setName(custom.getName());
                aux.setLastName(custom.getLastName());
                return aux;
       }).collect(Collectors.toList());
       
       return dto;
    }
    
    public CustomerDto getCustomer(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        
        if(customer.isPresent()){
            CustomerDto dto = new CustomerDto();
            dto.setIdCustomer(customer.get().getIdCustomer());
            dto.setDni(customer.get().getDni());
            dto.setName(customer.get().getName());
            dto.setLastName(customer.get().getLastName());
            return dto;
        }
        return null;
    }
    
    public String deleteCustomer(Long id){
        if(!customerRepository.existsById(id)){
            return "No existe el cliente que quiere eliminar";
        }
        customerRepository.deleteById(id);
        return "Se elimino con exito";
    }
    
    public CustomerDto updateCustomer(Long id, CustomerDto customer){
        if (!customerRepository.existsById(id)){
            return null;
        }
        return saveCustomer(customer);
    }
}
