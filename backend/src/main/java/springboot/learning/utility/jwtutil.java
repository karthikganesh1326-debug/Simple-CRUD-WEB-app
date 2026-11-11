package springboot.learning.utility;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class jwtutil {

    private final String SECRET = "spring course should be completed before diwali";
    private final int EXPIRY = 1000*60*60;

    private final Key SECRETKEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public String generatetoken(String email){
       return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRY))
                .signWith(SECRETKEY, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getemail(String token){
       return Jwts.parser()
                .setSigningKey(SECRETKEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token){
        try {
            getemail(token);
            return true;
        }
        catch (JwtException exception){
            return false;
        }
    }

}
