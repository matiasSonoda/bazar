
package com.api.bazar.service;

import com.api.bazar.entity.Sale;
import com.api.bazar.repository.SaleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    
    @Autowired
    private SaleRepository saleRepository;

    public Sale saveSale(Sale sale){
       return saleRepository.save(sale);
    }
    
    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }
    
    public Optional<Sale> getSale(Long id){
        return saleRepository.findById(id);
    }
    
    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }
    
    public Sale updateSale(Sale sale){
        if(!saleRepository.existsById(sale.getIdSale())){
            return null;
        }
        return saveSale(sale);
    }
}
