package com.example.module5.service;

import com.example.module5.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JWTService {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey createKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        // subject,claim,issuedAt and expiration acts as payload
        // signWith acts as signature
        // compact() generates the final JWT string in the format: Header.Payload.Signature
        return Jwts.builder() // builder() starts building a new JWT
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", Set.of("USER","ADMIN"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*60))) //valid up to 60 seconds
//                .signWith(createKey()) // this is default it uses hs256
                .signWith(createKey(),Jwts.SIG.HS512) // explicitly developers say that use hs512 without writing header code custom.
                .compact();
    }

    public Long getUserId(String token){
        //This method is used when client sends token.
        Claims claims = Jwts.parser() // parser() is used to parse (read and validate) a JWT
                //verifyWith() will say to parser that "Use this key when you verify the JWT"
                .verifyWith(createKey())
                .build()//create parser object
                .parseSignedClaims(token) // Parses the JWT, verifies the signature using the secret key,
                // checks expiration, and returns the signed JWT claims.
                .getPayload();// return claims. Claims = Payload
        return Long.valueOf(claims.getSubject());
    }
}
