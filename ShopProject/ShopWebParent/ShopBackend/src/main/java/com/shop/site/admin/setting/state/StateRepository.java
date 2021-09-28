package com.shop.site.admin.setting.state;

import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {

    public List<State> findByCountryOrderByNameAsc(Country country);


}
