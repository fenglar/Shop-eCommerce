package com.shop.admin.brand;

import com.shop.common.entity.Brand;
import com.shop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandRepository repo;

    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }
}
