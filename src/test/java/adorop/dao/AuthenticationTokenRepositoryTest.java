package adorop.dao;

import adorop.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

public class AuthenticationTokenRepositoryTest extends IntegrationTest {
    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    @Test
    public void shouldFindTokenByValue() throws Exception {
        assertNotNull(authenticationTokenRepository.findOneByValue("123"));
    }
}