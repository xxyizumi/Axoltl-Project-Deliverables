package com.example.mybatisadvanced.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisadvanced.entity.Product;
import com.example.mybatisadvanced.mapper.ProductMapper;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductMapper productMapper;
    
    public ProductController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productMapper.findById(id);
        return product != null 
            ? ResponseEntity.ok(product) 
            : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Product product) {
        productMapper.insert(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "商品を登録しました",
            "product", product
        ));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productMapper.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        productMapper.update(product);
        return ResponseEntity.ok(productMapper.findById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (productMapper.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productMapper.delete(id);
        return ResponseEntity.ok(Map.of("message", "商品を削除しました"));
    }

    @GetMapping("/find-by-condition")
    public List<Product> findByCondition(@RequestParam String name, @RequestParam Integer minPrice) {
        return productMapper.findByCondition(name, minPrice);
    }
    
}
