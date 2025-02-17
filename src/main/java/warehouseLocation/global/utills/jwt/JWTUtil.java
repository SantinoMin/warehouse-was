package warehouseLocation.global.utills.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  private Key key;

  public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

    byte[] byteSecretKey = Decoders.BASE64.decode(secret);
    key = Keys.hmacShaKeyFor(byteSecretKey);
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
  }

  public String getUserId(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userId", String.class);
  }

  public Boolean isExpired(String token) {

    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
  }

  public String createJwt(String username, Long userId, Long expiredMs) {

    Claims claims = Jwts.claims();
    claims.put("username", username);
    claims.put("userId", userId);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}