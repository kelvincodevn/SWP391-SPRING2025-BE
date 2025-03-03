package com.example.demo.api;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@SecurityRequirement(name = "api")
public class ProductAPI {
    @Autowired
    ProductService productService;
    @GetMapping
    public ResponseEntity getAllProduct(){
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody Product product){
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable long id){
        Product product = productService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }
}
