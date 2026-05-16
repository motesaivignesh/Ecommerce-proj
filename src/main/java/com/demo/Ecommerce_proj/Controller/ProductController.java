package com.demo.Ecommerce_proj.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Ecommerce_proj.Model.Product;
import com.demo.Ecommerce_proj.Service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet(){
        return "Hello";
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getall(){
         return new ResponseEntity<>(service.getallproducts(),HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product!=null) return new  ResponseEntity<>(product,HttpStatus.OK); // Sends HTTP 200 + Product Data
        else
     return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Sends HTTP 404 to React
    }
}
