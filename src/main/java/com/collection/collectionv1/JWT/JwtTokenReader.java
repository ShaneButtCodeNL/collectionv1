package com.collection.collectionv1.JWT;

import com.collection.collectionv1.Exceptions.JwtTokenException;
import com.collection.collectionv1.Exceptions.JwtTokenExpiredException;
import com.collection.collectionv1.Exceptions.JwtTokenNotFoundException;
import com.collection.collectionv1.Exceptions.JwtTokenNotValidUserException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenReader {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    @Value("${admin.username}")
    private String adminUsername;

    public boolean verifyJwtString(String jwtString) throws JwtTokenException {
        Date currDate = new Date();
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jwtString);
        } catch (SignatureException e){
            throw new JwtTokenNotFoundException("JWT not found.");
        }
        Claims body = claims.getBody();
        String subject = body.getSubject();
        Date exp = body.getExpiration();
        if (currDate.before(exp)) {
            if (adminUsername.equalsIgnoreCase(subject)) {
                return true;
            }else{
                throw new JwtTokenNotValidUserException("This user doesn't have authorization.");
            }
        }else{
            throw new JwtTokenExpiredException("JWT is expired.");
        }
    }

    public String getUserNameFromToken(String jwtString) throws JwtTokenException{
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jwtString).getBody();
        } catch (SignatureException e){
            throw new JwtTokenNotFoundException("JWT not found.");
        }
        return claims.getSubject();
    }
}
