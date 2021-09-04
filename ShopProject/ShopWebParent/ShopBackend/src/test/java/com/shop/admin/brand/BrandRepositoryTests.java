package com.shop.admin.brand;


import com.shop.common.entity.Brand;
import com.shop.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository repo;

    @Test
    public void testCreateBrand1() {
        Category laptops = new Category(6);
        Brand acer = new Brand("Acer");
        acer.getCategories().add(laptops);

        Brand savedBrand = repo.save(acer);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateBrand2() {
        Category cellphones = new Category(4);
        Category tablets = new Category(7);
        Brand apple = new Brand("Apple");

        apple.getCategories().add(cellphones);
        apple.getCategories().add(tablets);

        Brand savedBrand = repo.save(apple);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3() {

        Brand samsung = new Brand("Samsung");

        samsung.getCategories().add(new Category(29)); //category memory
        samsung.getCategories().add(new Category(24)); //category internal hard drive

        Brand savedBrand = repo.save(samsung);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);

    }

    @Test
    public void testFindAll() {
        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotNull();

    }

    @Test
    public void testGetById() {

        Brand brand = repo.findById(8).get();
        System.out.println(brand);
        assertThat(brand.getName()).isEqualTo("Acer");

    }

    @Test
    public void testUpdateName() {
        Brand samsung = repo.findById(3).get();
        String newName = "Samsung Electronics";
        samsung.setName(newName);

        Brand savedBrand = repo.save(samsung);
        assertThat(savedBrand.getName()).isEqualTo(newName);


    }

}