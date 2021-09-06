package com.shop.admin.brand;

import com.shop.admin.FileUploadUtil;
import com.shop.admin.category.CategoryNotFoundException;
import com.shop.admin.category.CategoryService;
import com.shop.common.entity.Brand;
import com.shop.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService service;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/brands")
    public String listAll(Model model) {
        List<Brand> listBrands = service.listAll();
        model.addAttribute("listBrands", listBrands);
        return "brands/brands";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create New Brand");

        return "brands/brand_form";
    }
    @GetMapping("/brands/save")
    public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
                            RedirectAttributes ra) throws IOException {
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);

            Brand savedBrand = service.save(brand);
            String uploadDir = "../brand-logos/" + savedBrand.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName,multipartFile);
        } else {
            service.save(brand);
        }
        ra.addFlashAttribute("message", "The brand has been saved successfully.");
        return "redirect:/brands";

    }
    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable(name="id") Integer id, Model model,
                            RedirectAttributes ra) {
        try {
            Brand brand = service.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("brand", brand);
            model.addAttribute("pageTitle", "Edit Brand (ID: " + id + " )");
            model.addAttribute("listCategories", listCategories);
            return "brands/brand_form";
        } catch (BrandNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            String brandDir = "../brand-images/" + id;
            FileUploadUtil.removeDir(brandDir);
            redirectAttributes.addFlashAttribute("message", "The brand ID " + id + "has been deleted succesfully");

        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/brands";

    }

}
