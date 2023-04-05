package com.kev.coop.security;

import com.kev.coop.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
  private final RedisTemplate<String, Object> redisTemplate;
  private static final int DURATION = 5; //5 minutes
  private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
    return generateToken(new HashMap<>(),user);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      User user
  ) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getId().toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * DURATION))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, User user) throws ParseException {
    String userId= extractUsername(token);
    Date tokenDateIssued = extractIssued(token);
    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    Object redisDateObj = redisTemplate.opsForValue().get(userId); // If token issued < time do not authenticate
    if(redisDateObj != null){
      Date redisDate = format.parse(redisDateObj.toString());
      if(tokenDateIssued.compareTo(redisDate) < 0){
        return false;
      }
    }
    return (userId.equals(user.getId().toString()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Date extractIssued(String token) {
    return extractClaim(token, Claims::getIssuedAt);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public static int getDURATION() {
    return DURATION;
  }
}
