package adorop.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class AuthenticationTokenRepositoryTest {
    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    @Test
    public void shouldFindTokenByValue() throws Exception {
        assertNotNull(authenticationTokenRepository.findOneByValue("123"));
    }
}