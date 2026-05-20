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
        return repo.findById(id).orElse(null); 
    }

    public Product addproduct(Product product, MultipartFile imgfile) throws IOException {
        product.setImageName(imgfile.getOriginalFilename());
        product.setImageType(imgfile.getContentType());
        product.setImageData(imgfile.getBytes());
        return repo.save(product);
    }

    public Product updateproduct(int id, Product product, MultipartFile imgfile) throws IOException {
        Product existingProduct = repo.findById(id).orElse(null);
        
        if (existingProduct != null) {
            if (imgfile != null && !imgfile.isEmpty()) {
                product.setImageData(imgfile.getBytes());
                product.setImageName(imgfile.getOriginalFilename());
                product.setImageType(imgfile.getContentType());
            } else {
                product.setImageData(existingProduct.getImageData());
                product.setImageName(existingProduct.getImageName());
                product.setImageType(existingProduct.getImageType());
            }
            product.setId(id);
            return repo.save(product);
        }
        return null;
    }

    public void deleteproductbyid(int id) {
        repo.deleteById(id);
    }
    public List<Product> searchProducts(String keyword){
        return  repo.searchProducts(keyword);
    }
}
