package com.shop.site.customer;

import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.Customer;
import com.shop.site.setting.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired private CountryRepository countryRepo;
    @Autowired private CustomerRepository customerRepo;

    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();

    }
    public boolean isEmailUnique(String email){
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }
}
