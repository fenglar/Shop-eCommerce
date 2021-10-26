package com.shop.site.admin.product;

import com.shop.site.common.entity.product.Product;
import com.shop.site.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping("/products/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
        return service.checkUnique(id, name);
    }

    @GetMapping("/products/get/{id}")
    public ProductDTO getProductInfo(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = service.get(id);
        return new ProductDTO(product.getName(), product.getMainImagePath(), product.getDiscountPrice(), product.getCost());
    }
}
