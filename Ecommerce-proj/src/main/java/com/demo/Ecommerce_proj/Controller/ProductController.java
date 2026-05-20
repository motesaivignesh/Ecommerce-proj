package com.demo.Ecommerce_proj.Controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String greet() {
        return "Hello";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getall() {
         return new ResponseEntity<>(service.getallproducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproduct(@PathVariable("id") int id) {
        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/product")
    public ResponseEntity<Product> addproduct(
            @RequestPart("product") Product product, 
            @RequestPart("imageFile") MultipartFile imgfile) {
        try {
            Product product1 = service.addproduct(product, imgfile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image") 
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getImagebyProductId(@PathVariable("id") int id) {
        Product product = service.getProductById(id);
        if (product == null || product.getImageData() == null || product.getImageType() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageFile = product.getImageData(); 
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") int id,
            @RequestPart("product") Product product, // Added named binding key
            @RequestPart(value = "imageFile", required = false) MultipartFile imgfile) { // Added named binding key and marked optional
        try {
            Product product2 = service.updateproduct(id, product, imgfile);
            if (product2 != null) {
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteproduct(@PathVariable("id") int id) {
        Product product = service.getProductById(id);
        if (product != null) {
            service.deleteproductbyid(id);
            return new ResponseEntity<>("product deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchproducts(@RequestParam("keyword") String keyword){
    List<Product> products = service.searchProducts(keyword);
    return new ResponseEntity<>(products,HttpStatus.OK);
        }
}
