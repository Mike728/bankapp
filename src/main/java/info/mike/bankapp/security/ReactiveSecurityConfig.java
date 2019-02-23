package info.mike.bankapp.security;

import info.mike.bankapp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ReactiveSecurityConfig implements WebFluxConfigurer {

    @Autowired
    private ReactiveTokenRepository reactiveTokenRepository;

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public ReactiveSecurityConfig(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Bean
    protected SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.csrf().disable()
            .securityContextRepository(reactiveTokenRepository)
            .authenticationManager(authenticationProvider())
            .authorizeExchange()
            .pathMatchers("/login").permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .anyExchange().authenticated()
            .and().build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProvider(tokenService, userRepository);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("Authorization");
    }
}
