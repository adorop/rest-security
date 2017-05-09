package adorop.security.authentication;

import adorop.model.AuthenticationToken;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class AuthorizationTokenAuthentication implements Authentication {
    private boolean isAuthenticated;
    @Setter
    private AuthenticationToken authenticationToken;
    private final String authenticationTokenValue;

    public AuthorizationTokenAuthentication(String authenticationTokenValue) {
        this.authenticationTokenValue = authenticationTokenValue;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return authenticationTokenValue;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authenticationToken.getUser();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return authenticationToken.getUser().getName();
    }
}
