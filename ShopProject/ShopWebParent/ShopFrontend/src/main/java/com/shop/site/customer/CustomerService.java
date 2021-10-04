package com.shop.site.customer;

import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.Customer;
import com.shop.site.setting.CountryRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();

    }

    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }

    public void registerCustomer(Customer customer) {
        encodePassword(customer);
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());

        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode);

        customerRepo.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
}