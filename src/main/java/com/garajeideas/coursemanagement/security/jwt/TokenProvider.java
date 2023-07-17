package com.garajeideas.coursemanagement.security.jwt;

import com.garajeideas.coursemanagement.management.SecurityMetersService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Configuration
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";
    private final Key key;
    private final JwtParser jwtParser;
    private final SecurityMetersService securityMetersService;
    private final long tokenValidity;

    public TokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration, SecurityMetersService securityMetersService) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidity = expiration;
        this.securityMetersService = securityMetersService;
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now + 1000 * this.tokenValidity);

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            trackError(e);
            log.trace(INVALID_JWT_TOKEN, e);
        }
        return false;
    }

    private void trackError(Exception e) {
        if (e instanceof ExpiredJwtException) {
            this.securityMetersService.trackTokenExpired();
        } else if (e instanceof UnsupportedJwtException) {
            this.securityMetersService.trackTokenUnsupported();
        } else if (e instanceof MalformedJwtException) {
            this.securityMetersService.trackTokenMalformed();
        } else if (e instanceof SignatureException) {
            this.securityMetersService.trackTokenInvalidSignature();
        }
    }
}
