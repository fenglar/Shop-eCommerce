package com.shop.site.admin.order;

import com.shop.site.admin.paging.PagingAndSortingHelper;
import com.shop.site.admin.paging.PagingAndSortingParam;
import com.shop.site.admin.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductSearchController {

    @Autowired
    private ProductService service;

    @GetMapping("/orders/search_product")
    public String showSearchProductPage() {
        return "orders/search_product";
    }

    @PostMapping("/orders/search_product")
    public String searchProducts(String keyword){
        return "redirect:/orders/search_product/page/1?sortField=name&sortDir=asc&keyword="+keyword;
    }

    @GetMapping("/orders/search_product/page/{pageNum}")
    public String searchProductByPage(@PagingAndSortingParam(listName = "listProducts", moduleURL = "/orders/search_product")
                                              PagingAndSortingHelper helper,
                                      @PathVariable(name = "pageNum") int pageNum) {

        service.searchProducts(pageNum, helper);
        return "orders/search_product";

    }
}
