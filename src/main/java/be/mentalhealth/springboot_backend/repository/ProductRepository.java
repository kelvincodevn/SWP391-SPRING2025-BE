package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
