package adorop.security.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


class AuthorizationHeaderAuthenticationFilter implements Filter {
    private final AuthenticationProvider authenticationProvider;
    private List<RequestMatcher> authenticationRequired;

    AuthorizationHeaderAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (authenticationIsRequired(request)) {
            Authentication authentication = authenticationProvider.authenticate(new AuthorizationTokenAuthentication(request.getHeader(HttpHeaders.AUTHORIZATION)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean authenticationIsRequired(HttpServletRequest request) {
        return authenticationRequired.stream()
                .anyMatch(requestMatcher -> requestMatcher.matches(request));
    }

    @Override
    public void destroy() {

    }

    public void setAuthenticationRequired(List<RequestMatcher> authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }
}
