package com.shop.site.category;

import com.shop.site.category.CategoryRepository;
import com.shop.site.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repo;

    @Test
    public void testListEnabledCategories(){
        List<Category> categories = repo.findAllEnabled();
        categories.forEach(category -> {
            System.out.println(category.getName()+" ("+category.isEnabled()+")");
        });
    }
    @Test
    public void testFindCategoryByAlias(){
        String alias="electronics";
        Category category = repo.findByAliasEnabled(alias);

        assertThat(category).isNotNull();
    }

}
