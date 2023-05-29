package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @GetMapping("/login")
//    public String loginForm(Model model){
//        return "login";
//    }
    @GetMapping(value = {"/login", "/"})
    public String loginForm(Model model, @RequestParam(name = "username", defaultValue = "Nghiem Nam") String username) {
        model.addAttribute("username", username);
        return "login";
    }

    @GetMapping("/sign-up")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "sign-up";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    @PostMapping("/sign-up-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "sign-up";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message", "Your email has been registered");
                return "sign-up";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
                return "redirect:/login";
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "sign-up";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Can not register because error server!");
        }
        return "sign-up";
    }
}
