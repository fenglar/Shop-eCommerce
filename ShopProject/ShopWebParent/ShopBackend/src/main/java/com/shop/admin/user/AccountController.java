package com.shop.admin.user;

import com.shop.admin.security.ShopUserDetails;
import com.shop.common.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    private UserService service;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopUserDetails loggedUser, Model model) {
String email=loggedUser.getUsername();
User user = service.getByEmail(email);
model.addAttribute("user",user);

return "account_form";
    }
    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal ShopUserDetails loggedUser) {
        System.out.println(user);
        service.updateAccount(user);
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        redirectAttributes.addFlashAttribute("message", "Your account details have been updated");

        return "redirect:/account";
    }
}
