package com.shop.site.category;


import com.shop.site.common.entity.setting.Setting;
import com.shop.site.common.entity.setting.SettingCategory;
import com.shop.site.setting.SettingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

    @Autowired
    SettingRepository repo;

    @Test
    public void testFindByTwoCategories(){
        List<Setting> settings = repo.findByTwoCategories(SettingCategory.GENERAL,SettingCategory.CURRENCY);
        settings.forEach(System.out::println);

    }

}
