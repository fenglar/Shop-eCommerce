package com.shop.admin.product;

import com.shop.admin.brand.BrandNotFoundException;
import com.shop.admin.brand.BrandNotFoundRestException;
import com.shop.admin.brand.BrandService;
import com.shop.admin.category.CategoryDTO;
import com.shop.common.entity.Brand;
import com.shop.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping("/products/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
        return service.checkUnique(id, name);
    }

}
