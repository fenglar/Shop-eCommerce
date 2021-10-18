package com.shop.site.address;

import com.shop.site.common.entity.Address;
import com.shop.site.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired private AddressRepository repo;

    public List<Address> listAddressBook(Customer customer){
        return repo.findByCustomer(customer);
    }



}
