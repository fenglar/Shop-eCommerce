package com.shop.site.setting;

import com.shop.site.common.entity.setting.Setting;
import com.shop.site.common.entity.setting.SettingBag;

import java.util.List;

public class CurrencySettingBag extends SettingBag {

    public CurrencySettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getSymbol(){
        return super.getValue("CURRENCY_SYMBOL");
    }
    public String getSymbolPosition(){
        return super.getValue("CURRENCY_SYMBOL_POSITION");
    }
    public String getDecimalPointType(){
        return super.getValue("DECIMAL_POINT_TYPE");
    }
    public String getThousandsPointType(){
        return super.getValue("THOUSANDS_POINT_TYPE");
    }
    public Integer getDecimalDigits(){
        return Integer.parseInt(super.getValue("DECIMAL_DIGITS"));
    }
}
