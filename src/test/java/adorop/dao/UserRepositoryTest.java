package adorop.dao;

import adorop.IntegrationTest;
import adorop.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertSame;

public class UserRepositoryTest extends IntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() throws Exception {
        userRepository.save(new User(3L, "al", 28));
        assertSame(3L, userRepository.count());
    }
}