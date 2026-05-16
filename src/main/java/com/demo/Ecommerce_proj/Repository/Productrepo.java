package com.demo.Ecommerce_proj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.Ecommerce_proj.Model.Product;
@Repository
public interface Productrepo extends JpaRepository<Product, Integer> {

}