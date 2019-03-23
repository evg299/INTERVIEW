package ru.sputnik.interview.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sputnik.interview.demo.model.Product;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {
}
