package com.shop.admin.product;

import com.shop.common.entity.Brand;
import com.shop.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    public Long countById(Integer id);

    public Brand findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);

}
