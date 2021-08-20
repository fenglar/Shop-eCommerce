package com.shop.admin.category;


import com.shop.common.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
