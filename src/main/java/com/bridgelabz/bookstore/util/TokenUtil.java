package com.bridgelabz.bookstore.util;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private static final String SECRET_TOKEN = "bookstore";

    public String createToken(int id) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);
        return JWT.create().withClaim("id", id).sign(algorithm);
    }

    public int decodeToken(String token) throws SignatureVerificationException {
        Verification verification = JWT.require(Algorithm.HMAC256(SECRET_TOKEN));
        JWTVerifier jwtVerifier = verification.build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claimId = decodedJWT.getClaim("id");
        int id = claimId.asInt();
        return id;
    }

    public static void main(String[] args) {
        TokenUtil util = new TokenUtil();
        System.out.println(util.decodeToken(util.createToken(1)));
    }
}
