package adorop.dao;


import adorop.model.AuthenticationToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken, Long> {
    AuthenticationToken findOneByValue(String value);
}
