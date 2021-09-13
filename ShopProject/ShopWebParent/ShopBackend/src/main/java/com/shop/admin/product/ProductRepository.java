package com.shop.admin.product;

import com.shop.common.entity.Brand;
import com.shop.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    public Long countById(Integer id);

    public Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);

    @Query("UPDATE Product p SET p.enabled= ?2 WHERE p.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
