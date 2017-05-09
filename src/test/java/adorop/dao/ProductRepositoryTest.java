package adorop.dao;

import adorop.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void name() throws Exception {
        assertTrue(productRepository.exists(1L));
    }

    @Test
    public void testSave() throws Exception {
        Product product = new Product();
        product.setId(4L);
        product.setTitle("test");
        product.setPrice(new BigDecimal("100"));
        product.setOwner(userRepository.findOne(1L));
        productRepository.save(product);
        assertSame(3L, productRepository.count());
    }
}