package Mining;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public class Decrypting {

    private static Optional<String> createJWT(String secret) {
        Algorithm algorithmHS;
        Optional<String> token = Optional.empty();
        try {
            algorithmHS = Algorithm.HMAC256(secret);
            token = Optional.of(JWT.create().withIssuer("ITWS").sign(algorithmHS));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    private static boolean verifyJWT(String token, String secret) {
        Algorithm algorithmHS;
        try {
            algorithmHS = Algorithm.HMAC256(secret);
            JWT.require(algorithmHS).withIssuer("ITWS").build().verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){
        Optional<String> jwt  = createJWT("test");
        if (jwt.isPresent()) {
            if (verifyJWT(jwt.get(), "test")) {
                System.out.println("Cracked!");
            } else {
                System.out.println("Try Again");
            }
        }
    }
}
