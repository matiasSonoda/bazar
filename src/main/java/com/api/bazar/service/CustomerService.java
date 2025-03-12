
package com.api.bazar.service;

import com.api.bazar.entity.Customer;
import com.api.bazar.repository.CustomerRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    
    public List<Customer> getAllCustomer(){
       return customerRepository.findAll();
    }
    
    public Optional<Customer> getCustomer(Long id){
        return customerRepository.findById(id);
    }
    
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
    
    public Customer updateCustomer(Long id, Customer customer){
        if (!customerRepository.existsById(id)){
            return null;
        }
        return saveCustomer(customer);
    }
}
