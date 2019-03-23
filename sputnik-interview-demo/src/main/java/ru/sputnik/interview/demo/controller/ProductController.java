package ru.sputnik.interview.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sputnik.interview.demo.model.Product;
import ru.sputnik.interview.demo.repo.ProductRepo;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getAll() {
        return productRepo.findAll();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Product saveProduct(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
    }
}
