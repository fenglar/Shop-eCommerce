package com.shop.site.admin.setting;

import com.shop.site.common.entity.Setting;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, String> {

}
