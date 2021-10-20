package com.shop.site.shipping;

import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.ShippingRate;
import com.shop.site.common.entity.State;
import org.springframework.data.repository.CrudRepository;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

    public ShippingRate findByCountryAndState(Country country, String state);
}
