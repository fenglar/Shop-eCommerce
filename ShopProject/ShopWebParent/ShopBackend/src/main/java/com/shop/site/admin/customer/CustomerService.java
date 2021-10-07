package com.shop.site.admin.customer;

import com.shop.site.admin.paging.PagingAndSortingHelper;
import com.shop.site.admin.paging.PagingAndSortingParam;
import com.shop.site.admin.setting.country.CountryRepository;
import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.Customer;
import com.shop.site.common.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {
    public static final int CUSTOMERS_PER_PAGE = 10;

    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
    }

    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepo.updateEnabledStatus(id, enabled);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        try {
            return (Customer) customerRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CustomerNotFoundException("Could not find any customer with ID: " + id);
        }
    }

    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();

    }

    public boolean isEmailUnique(Integer id, String email) {
        Customer existCustomer = customerRepo.findByEmail(email);

        if (existCustomer == null && existCustomer.getId() != id) {
            return false;
        }
        return true;
    }

    public void save(Customer customerInForm) {
        Customer customerInDB = (Customer) customerRepo.findById(customerInForm.getId()).get();

        if (!customerInForm.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
            customerInForm.setPassword(encodedPassword);
        } else {
            customerInForm.setPassword(customerInDB.getPassword());
        }
        customerInForm.setEnabled(customerInDB.isEnabled());
        customerInForm.setCreatedTime(customerInDB.getCreatedTime());
        customerInForm.setVerificationCode(customerInDB.getVerificationCode());

        customerRepo.save(customerInForm);
    }

    public void delete(Integer id) throws CustomerNotFoundException {
        Long count = customerRepo.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoundException("Could not find any customer with ID: " + id);
        }
        customerRepo.deleteById(id);
    }


}
