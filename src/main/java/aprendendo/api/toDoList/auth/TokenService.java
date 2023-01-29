package aprendendo.api.toDoList.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import aprendendo.api.toDoList.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exp}")
    private String exp;

    public String generate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        long expiration = new Date().getTime();
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",user.toDTO());

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(expiration + Long.valueOf(exp)))
                .setSubject(user.getId().toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public boolean isValid(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception exc) {
            System.out.println(exc.getMessage());
            return false;
        }
    }

    public long getIdFromToken(String token) {
        String id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return Long.valueOf(id);
    }
}
