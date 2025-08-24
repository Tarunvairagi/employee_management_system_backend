package com.emp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTService {
    private static final String SECRET_KEY = "my-super-secret-key";
    private static final long EXPIRATION_TIME = 86400000;

    //Generating the token
    public String generateToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    //Validate Token And Retrieve Subject
    public String validateTokenAndRetrieveSubject(String token, UserDetails userDetails) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);

        String subject = jwt.getSubject();

        // Optional validation logic using userDetails
        if (userDetails != null && !subject.equals(userDetails.getUsername())) {
            throw new RuntimeException("Token subject does not match user");
        }

        return subject;
    }

}
