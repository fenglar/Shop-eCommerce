package com.shop.site.admin.customer;


import com.shop.site.common.entity.Country;
import com.shop.site.common.entity.Customer;
import com.shop.site.common.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping("/customers")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "firstName", "asc", null);
    }

    @GetMapping("/customers/page/{pageNum}")
    public String listByPage(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
        Page<Customer> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Customer> listCustomers = page.getContent();

        long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
        model.addAttribute("startCound", startCount);

        long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("moduleURL", "/customers");

        return "customers/customers";
    }

    @GetMapping("/customers/{id}/enabled/{status}")
    public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        service.updateCustomerEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The category ID " + id + "has been " + status;
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/customers";
    }

    @GetMapping("/customers/detail/{id}")
    public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Customer customer = service.get(id);
            List<Country> countries = service.listAllCountries();

            model.addAttribute("listCountries", countries);
            model.addAttribute("customer", customer);
            model.addAttribute("pageTitle", String.format("Edit Customer(ID: %d)", id));

            return "redirect:/customers";
        } catch (CustomerNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/customers";
        }
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer, Model model, RedirectAttributes ra) {

        service.save(customer);
        ra.addFlashAttribute("message", "The customer ID" + customer.getId() + "has been updated");

        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id,
                                 RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The customer ID " + id + "has been deleted succesfully");

        } catch (CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/customers";

    }

}
