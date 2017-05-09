package adorop.security.authentication;

import adorop.dao.AuthenticationTokenRepository;
import adorop.model.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class AuthorizationHeaderAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    public AuthorizationHeaderAuthenticationProvider(AuthenticationTokenRepository authenticationTokenRepository) {
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthorizationTokenAuthentication authorizationTokenAuthentication = (AuthorizationTokenAuthentication) authentication;
        String tokenValue = (String) ofNullable(authentication.getCredentials())
                .orElseThrow(() -> new BadCredentialsException("token is null"));
        authorizationTokenAuthentication.setAuthenticated(true);
        AuthenticationToken authenticationToken = ofNullable(authenticationTokenRepository.findOneByValue(tokenValue))
                .orElseThrow(() -> new BadCredentialsException("token isn't valid"));
        authorizationTokenAuthentication.setAuthenticationToken(authenticationToken);
        return authorizationTokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == AuthorizationTokenAuthentication.class;
    }
}
