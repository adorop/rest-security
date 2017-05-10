package adorop.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationHeaderAuthenticationFilterBuilder {
    private final List<RequestMatcher> authenticationRequired = new ArrayList<>();
    private AuthenticationProvider authenticationProvider;

    public AuthorizationHeaderAuthenticationFilterBuilder setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        return this;
    }

    public AuthorizationHeaderAuthenticationFilterBuilder addAuthenticationRequiredAntPattern(String pattern) {
        authenticationRequired.add(new AntPathRequestMatcher(pattern));
        return this;
    }

    public AuthorizationHeaderAuthenticationFilterBuilder addAuthenticationRequiredAntPattern(String pattern,
                                                                                              String httpMethod) {
        authenticationRequired.add(new AntPathRequestMatcher(pattern, httpMethod, false));
        return this;
    }

    public AuthorizationHeaderAuthenticationFilter build() {
        AuthorizationHeaderAuthenticationFilter filter = new AuthorizationHeaderAuthenticationFilter(authenticationProvider);
        filter.setAuthenticationRequired(authenticationRequired);
        return filter;
    }
}
