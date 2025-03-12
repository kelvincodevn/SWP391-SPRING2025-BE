package be.mentalhealth.springboot_backend.repository;


import be.mentalhealth.springboot_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);

    List<Product> findProductsByIsDeletedFalse();
}
