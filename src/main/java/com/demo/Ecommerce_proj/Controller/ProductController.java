package com.demo.Ecommerce_proj.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Ecommerce_proj.Model.Product;
import com.demo.Ecommerce_proj.Service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet(){
        return "Hello";
    }
    @GetMapping("/products")
    public List<Product> getall(){
        return service.getallproducts();
    }
}
