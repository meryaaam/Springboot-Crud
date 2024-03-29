package springboot.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.

bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.model.Product;
import springboot.repository.ProductRepository;


@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/api")
public class ProductController {
	
	 @Autowired
	  ProductRepository productRepository;
	
	 @GetMapping("/products")
	  public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) 
	 {
	    try {
	      List<Product> products = new ArrayList<Product>();

	      if (name == null)
	    	  productRepository.findAll().forEach(products::add);
	      else
	    	  productRepository.findByname(name).forEach(products::add);

	      if (products.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(products, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 @PostMapping("/products")
	  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
	    try {
	    	Product _product = productRepository
	          .save(new Product(product.getName(), product.getPrice(), product.getQt() , product.getCat() ));
	      return new ResponseEntity<>(_product, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	  }
	 
	 @DeleteMapping("/products")
	  public ResponseEntity<HttpStatus> deleteAllProducts() {
	    try {
	    	productRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }

	  }
	
	 @PutMapping("/products/{id}")
	  public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
	    Optional<Product> productData = productRepository.findById(id);

	    if (productData.isPresent()) {
	    	Product _Product = productData.get();
	    	_Product.setName(product.getName());
	    	_Product.setPrice(product.getPrice());
	    	_Product.setQt(product.getQt());
	    	_Product.setCat(product.getCat());
	      return new ResponseEntity<>(productRepository.save(_Product), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	 
	 
	 
	 
	 @GetMapping("/products/name")
	  public ResponseEntity<List<Product>> findByname(String name) {
	    try {
	      List<Product> products = productRepository.findByname(name);

	      if (products.isEmpty()){
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(products, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	  }
	 
	 
	 @GetMapping("/products/{id}")
	  public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
	    Optional<Product> productData = productRepository.findById(id);

	    if (productData.isPresent()) {
	      return new ResponseEntity<>(productData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	 
	 @DeleteMapping("/products/{id}")
	  public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
	    try {
	    	productRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	  }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}