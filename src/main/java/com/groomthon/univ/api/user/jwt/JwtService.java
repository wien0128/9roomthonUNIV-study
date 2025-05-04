package com.groomthon.univ.api.user.jwt;

import com.groomthon.univ.common.exception.UnauthorizedException;
import com.groomthon.univ.common.response.ErrorStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

@Service
@Getter
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpirationPeriod;

    public JwtService(@Value("${jwt.secretkey}") String secretKey,
                      @Value("${jwt.access.expiration}") long accessTokenExpirationPeriod) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
    }

    public String generateToken(String email, String role) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationPeriod);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuer("groomthon")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token: " + e.getMessage());
            throw new UnauthorizedException(ErrorStatus.TOKEN_EXPIRED.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT token: " + e.getMessage());
            throw new UnauthorizedException(ErrorStatus.INVALID_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token: " + e.getMessage());
            throw new UnauthorizedException(ErrorStatus.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Empty JWT token: " + e.getMessage());
            throw new UnauthorizedException(ErrorStatus.EMPTY_TOKEN.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        User principal = new User(email, "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ role)));

        return new UsernamePasswordAuthenticationToken(
                principal,
                token,
                principal.getAuthorities()
        );
    }
}
