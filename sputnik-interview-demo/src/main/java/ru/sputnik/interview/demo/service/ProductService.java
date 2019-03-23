package ru.sputnik.interview.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sputnik.interview.demo.model.Color;
import ru.sputnik.interview.demo.model.KindOfClothes;
import ru.sputnik.interview.demo.model.Place;
import ru.sputnik.interview.demo.model.Product;
import ru.sputnik.interview.demo.repo.ProductRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @PostConstruct
    private void generateSomeProducts() {
        Random random = new Random(System.nanoTime());

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setColor(Color.values()[random.nextInt(Color.values().length)]);
            product.setKindOfClothes(KindOfClothes.values()[random.nextInt(KindOfClothes.values().length)]);
            product.setPlace(Place.values()[random.nextInt(Place.values().length)]);
            product.setSize(42 + random.nextInt(13));
            product.setCost(BigDecimal.valueOf(1000 * random.nextDouble()));
            product.setDescription("Desc #" + i);

            productRepo.save(product);
        }
    }
}
