package com.shop.site.setting;

import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {

    public List<State> findByCountryOrderByNameAsc(Country country);


}
