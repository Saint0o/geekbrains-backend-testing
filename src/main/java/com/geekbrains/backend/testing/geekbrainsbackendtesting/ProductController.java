package com.geekbrains.backend.testing.geekbrainsbackendtesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    @PostMapping
    public Product saveProducts(@RequestBody Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    //Обработать запрос вида: GET /products/filtered?min_price=100. В результате должен вернуться список товаров,
    //цена которых >= 100
    @GetMapping("/filtered")
    public List<Product> getAllProductsMinPrice(@RequestParam(name = "min_price") int minPrice) {
        return productRepository.findAll().stream()
                .filter(product -> product.getPrice() >= minPrice)
                .collect(Collectors.toList());
    }

    //Обработать запрос вида: GET /products/delete/1. В результате должен удалиться товар с соответствующим id
    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product #" + id  + " deleted successfully";
    }

    //* Попробуйте реализовать возможность изменения названия товара по id. Что-то вроде: /products/{id}/change_title...
    @GetMapping("/{id}/change_title")
    public String changeProductTitleById(@PathVariable Long id, @RequestParam String title) {
        Product product = productRepository.findById(id).get();
        product.setTitle(title);
        productRepository.save(product);
        return "Product #" + id + " title changed successfully to " + title;
    }
}
