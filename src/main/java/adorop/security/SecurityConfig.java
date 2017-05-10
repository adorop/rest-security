package adorop.security;

import adorop.security.authentication.AuthorizationHeaderAuthenticationFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.servlet.Filter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@ComponentScan("adorop.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter authorizationHeaderAuthenticationFilter = getAuthenticationFilter();
        http.addFilterAfter(authorizationHeaderAuthenticationFilter, ExceptionTranslationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                .and()
                    .securityContext().securityContextRepository(new NullSecurityContextRepository())
                .and()
                    .requestCache().requestCache(new NullRequestCache())
                .and()
                    .csrf().disable();
    }

    private Filter getAuthenticationFilter() {
        return new AuthorizationHeaderAuthenticationFilterBuilder()
                .setAuthenticationProvider(authenticationProvider)
                .addAuthenticationRequiredAntPattern("/users/**")
                .addAuthenticationRequiredAntPattern("/products/**", "POST")
                .build();
    }
}
