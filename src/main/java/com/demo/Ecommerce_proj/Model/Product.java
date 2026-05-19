package com.demo.Ecommerce_proj.Model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "product_description")
    private String description; 
    private String brand;
    private Integer price;
    private String category;
    private Date releaseDate;
    private boolean productAvailable; 
    private Integer stockQuantity; 
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
