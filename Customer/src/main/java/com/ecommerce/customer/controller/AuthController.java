package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model, Principal principal, HttpSession session){
        model.addAttribute("title", "Login");
        return "login";
    }
    @GetMapping("/sign-up")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "sign-up";
    }
    @PostMapping("/sign-up-new")
    public String addNewAdmin(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "sign-up";
            }
            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDto", customerDto);
                redirectAttributes.addFlashAttribute("message", "Your email has been registered");
                return "sign-up";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("customerDto", customerDto);
                return "redirect:/login";
            } else {
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "sign-up";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Can not register because error server!");
        }
        return "sign-up";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }
}
