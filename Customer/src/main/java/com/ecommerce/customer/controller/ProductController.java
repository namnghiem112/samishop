package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model, Principal principal){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }
}
