package be.mentalhealth.springboot_backend.service;


import be.mentalhealth.springboot_backend.entity.Product;
import be.mentalhealth.springboot_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findProductsByIsDeletedFalse();
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product deleteProduct(long id){
        Product product = productRepository.findProductById(id);
        product.isDeleted = true;
        return productRepository.save(product);
    }
}
