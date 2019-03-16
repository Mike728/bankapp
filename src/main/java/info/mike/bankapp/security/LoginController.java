package info.mike.bankapp.security;

import info.mike.bankapp.security.web.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationProvider authenticationProvider;
    private final ReactiveTokenRepository reactiveTokenRepository;

    public LoginController(AuthenticationProvider authenticationProvider, ReactiveTokenRepository reactiveTokenRepository) {
        this.authenticationProvider = authenticationProvider;
        this.reactiveTokenRepository = reactiveTokenRepository;
    }

    @PostMapping(value = "/login")
    public Mono<?> loginRequest(@RequestBody LoginRequest loginRequest, ServerWebExchange serverWebExchange){
        String principal = loginRequest.getPin();

        logger.info("Received login request: " + loginRequest);
        return authenticationProvider
            .authenticate(new UsernamePasswordAuthenticationToken(principal, loginRequest.getPassword(), Collections.EMPTY_LIST))
            .flatMap(authentication -> reactiveTokenRepository
                .save(serverWebExchange, new SecurityContextImpl(authentication))
                .then(Mono.just(authentication.getCredentials().toString())));
    }
}
