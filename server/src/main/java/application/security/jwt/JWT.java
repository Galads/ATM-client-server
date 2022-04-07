package application.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public class JWT {
    private String KEY = "secret+key_";

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    private String createToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)                                                   // не обязательно
                .setIssuedAt(new Date())                                                // не обязательно
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 1000))    // не обязательно
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    public boolean validateUsername(String username, UserDetails userDetails) {
        return getUsername(username).equals(userDetails.getUsername());
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getOverDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String username, Function<Claims, T> claimMethod) {
        Claims claims = getBodyJWT(username);
        return claimMethod.apply(claims);
    }

    private Claims getBodyJWT(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}