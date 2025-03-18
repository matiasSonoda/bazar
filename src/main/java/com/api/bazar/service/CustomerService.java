
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
    
    public Customer saveCustomer(CustomerDto customer){
        Customer request = new Customer();
        request.setDni(customer.getDni());
        request.setName(customer.getName());
        request.setLastName(customer.getLastName());
        return customerRepository.save(request);
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
    
    public Optional<Customer> getCustomer(Long id){
        return customerRepository.findById(id);
    }
    
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
    
    public Customer updateCustomer(Long id, CustomerDto customer){
        if (!customerRepository.existsById(id)){
            return null;
        }
        return saveCustomer(customer);
    }
}
