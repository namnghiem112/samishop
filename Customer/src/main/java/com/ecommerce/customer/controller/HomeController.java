package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String index(Model model, Principal principal, HttpSession session){
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        String firstName = "Tài";String lastName = "Khoản";
        String loginPattern = "Đăng Nhập";
        String link = "/login";
        session.setAttribute("account",firstName+" "+lastName);
        session.setAttribute("link",link);
        session.setAttribute("loginPattern",loginPattern);
        return "index";
    }
    @RequestMapping("/index")
    public String home(Model model, Principal principal, HttpSession session) {
        model.addAttribute("title", "Home Page");
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username);
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String loginPattern = "Đăng xuất";
        String link = "/";
        session.setAttribute("account",firstName+" "+lastName);
        session.setAttribute("link",link);
        session.setAttribute("loginPattern",loginPattern);
        return "index";
    }
}
