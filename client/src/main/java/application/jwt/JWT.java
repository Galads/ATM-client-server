package application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public class JWT {
    private String KEY = "secret+key_";

    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(HashMap<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)                                                   // не обязательно
                .setIssuedAt(new Date())                                                // не обязательно
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 1000))    // не обязательно
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    public boolean validateUsername(String username, UserDetails userDetails) {
        return getUsername(username).equals(userDetails.getUsername());
    }

    public String getUsername(String token) {
        return getClaim(token, claims -> claims.getSubject());
    }

    public Date getOverDate(String token) {
        return getClaim(token, claims -> claims.getExpiration());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimMethod) {
        Claims claims = getBodyJWT(token);
        return claimMethod.apply(claims);
    }

    private Claims getBodyJWT(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
