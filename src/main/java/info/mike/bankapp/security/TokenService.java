package info.mike.bankapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private static final String KEY = "dgh256e23";

    protected Authentication generateToken(Authentication authentication, Collection<? extends GrantedAuthority> authorityList){
        String token = Jwts.builder()
            .setSubject(authentication.getPrincipal().toString())
            .addClaims(Collections.emptyMap())
            .setExpiration(Date.from(LocalDateTime.now()
                .plusYears(30L)
                .atZone(ZoneId.systemDefault())
                .toInstant()))
            .signWith(SignatureAlgorithm.HS512, KEY.getBytes(StandardCharsets.UTF_8))
            .compact();

        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), token, authentication.getAuthorities());
    }

    public static String getLoginFromToken(String token){
        if(token != null && Strings.isNotEmpty(token)) {
            token = token.replace("Bearer", "");
            return Jwts.parser()
                .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        }
        throw new IllegalArgumentException("JWT Service: Cannot parse JWT");
    }
}
