package com.demo.Ecommerce_proj.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.Ecommerce_proj.Model.Product;
import com.demo.Ecommerce_proj.Repository.Productrepo;

@Service
public class ProductService {
    @Autowired
    private Productrepo repo;
    public List<Product> getallproducts() {
    return repo.findAll();
    }

    public Product getProductById(int id) {
        // repo.findById(id) looks inside your H2 database
        return repo.findById(id).orElse(null); 
    }

   public Product addproduct(Product product, MultipartFile imgfile) throws IOException {
    product.setImageName(imgfile.getOriginalFilename());
    product.setImageType(imgfile.getContentType());
    product.setImageData(imgfile.getBytes());
    return repo.save(product);
}

}
