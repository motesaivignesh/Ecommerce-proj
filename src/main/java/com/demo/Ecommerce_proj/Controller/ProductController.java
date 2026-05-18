package com.demo.Ecommerce_proj.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.Ecommerce_proj.Model.Product;
import com.demo.Ecommerce_proj.Service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
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
    
   @PostMapping("/product")
public ResponseEntity<Product> addproduct(
        @RequestPart("product") Product product, 
        @RequestPart("imageFile") MultipartFile imgfile) { // <-- Key fix from "imgFile" to "imageFile"
    try {
        Product product1 = service.addproduct(product, imgfile);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    } catch (Exception e) {
        // Keeps track of the stack trace in your terminal if any secondary errors occur
        e.printStackTrace(); 
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
@GetMapping("/product/{id}/image") 
@Transactional(readOnly = true)
public ResponseEntity<byte[]> getImagebyProductId(@PathVariable int id) {
    Product product = service.getProductById(id);
    if (product == null || product.getImageData() == null || product.getImageType() == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    byte[] imageFile = product.getImageData(); 
    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(product.getImageType()))
            .body(imageFile);
}

}
