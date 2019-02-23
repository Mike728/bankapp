package info.mike.bankapp.security;

import info.mike.bankapp.domain.UserNotFoundException;
import info.mike.bankapp.domain.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class AuthenticationProvider implements ReactiveAuthenticationManager {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationProvider(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        return verifyThatUserExistInDatabase(name, password)
            .map(auth -> tokenService.generateToken(auth, auth.getAuthorities()));
    }

    private Mono<Authentication> verifyThatUserExistInDatabase(String login, String password){
        return userRepository.findByLogin(login)
            .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
            .flatMap(user -> {
            if (user.getPassword().equals(password)) {
                return Mono.just(new UsernamePasswordAuthenticationToken(login, password, Collections.emptyList()));
            }
            return Mono.error(new IllegalArgumentException("Invalid username or password"));
        });
    }
}
