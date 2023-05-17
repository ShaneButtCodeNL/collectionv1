package com.collection.collectionv1.JWT;

import com.collection.collectionv1.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenGenerator {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    public String createToken(User user){
        Date iss = new Date();
        //@Value("${jwt.secret)")
        // private String secret;
        int ONE_DAY = 1000 * 60 * 60 * 24;
        Date exp = new Date(new Date().getTime() + ONE_DAY);
        return Jwts.builder().setIssuer("SERVER").setSubject(user.getUserName()).setExpiration(exp).setIssuedAt(iss).signWith(getKey()).compact();
    }
}
