package info.mike.bankapp.security;

import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ReactiveTokenRepository implements ServerSecurityContextRepository {

    private final String KEY = "dgh256e23";

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        serverWebExchange
            .getResponse()
            .getHeaders()
            .add("Authorization", "Bearer "  + securityContext.getAuthentication().getCredentials());
        SecurityContextHolder.setContext(securityContext);
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        String token = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");

        if(token != null && Strings.isNotEmpty(token)){
            token = token.replace("Bearer", "");
        }

        if(token != null && Strings.isNotEmpty(token)){
            String user = Jwts.parser()
                .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

            if(user != null) {
                return Mono.just(new SecurityContextImpl(new UsernamePasswordAuthenticationToken(user, token, Collections.emptyList())));
            } else {
                return Mono.error(new IllegalArgumentException("User cannot be found"));
            }
        }

        return Mono.empty();
    }
}
