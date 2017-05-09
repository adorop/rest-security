package adorop.web;

import adorop.dao.ProductRepository;
import adorop.dto.ProductDto;
import adorop.model.Product;
import adorop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Product getBy(@PathVariable Long id) {
        return productRepository.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody ProductDto productDto, @AuthenticationPrincipal User user) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        product.setOwner(user);
        return productRepository.save(product);
    }
}
