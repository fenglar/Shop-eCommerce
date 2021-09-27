package com.shop.site.admin.setting;

import com.shop.site.admin.FileUploadUtil;
import com.shop.site.common.entity.Currency;
import com.shop.site.common.entity.Setting;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SettingController {

    @Autowired
    private SettingService service;

    @Autowired
    private CurrencyRepository currencyRepo;

    @GetMapping("/settings")
    public String listAll(Model model) {
        List<Setting> listSettings = service.listAllSettings();
        List<Currency> listCurrencies = currencyRepo.findAllByOrderByNameAsc();

        model.addAttribute("listCurrencies", listCurrencies);

        for (Setting setting : listSettings) {
            model.addAttribute(setting.getKey(), setting.getValue());
        }

        return "settings/settings";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(@RequestParam("fileImage") MultipartFile multipartFile,
                                      HttpServletRequest request, RedirectAttributes ra) throws IOException {
        GeneralSettingBag settingBag = service.getGeneralSettings();
        setSiteLogo(multipartFile, settingBag);
        saveCurrencySymbol(request, settingBag);
        updateSettingValuesFromForm(request, settingBag.list());
        ra.addFlashAttribute("message", "General settings have been saved.");
        return "redirect:/settings";
    }

    private void setSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String value = "/site-logo." + fileName;
            settingBag.updateSiteLogo(value);
            String uploadDir = "../site-logo/";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
    }

    private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag settingBag){
        Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByIdResult = currencyRepo.findById(currencyId);

        if(findByIdResult.isPresent()){
            Currency currency = findByIdResult.get();

            settingBag.updateCurrencySymbol(currency.getSymbol());
        }
    }
    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings){
        for(Setting setting : listSettings){
            String value=request.getParameter(setting.getKey());
            if(value != null) {
                setting.setValue(value);
            }
        }
        service.saveAll(listSettings);
    }
}
