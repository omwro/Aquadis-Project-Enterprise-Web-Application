package hva.nl.api.controllers;

import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Controller
public class TokenController {

    //Broken for some reason
    //@Value("${jwt.secret}")
    // static String secret;

    private final String secret = "Whotouchemahspagget";

    @Autowired
    private UserRepository userRepository;

    public long sessionMaxTime = 3600000; //1 hour
    private Long tokenCounter = Long.MIN_VALUE;

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);

        try {
            User user = userRepository.getUserByID(Integer.parseInt(claims.getSubject()));
            if (user == null){
                return false;
            }
            if (user.getCurrentToken().equals(token) == false){
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        if (claims.getExpiration().before(new Date(System.currentTimeMillis()))){
            return false;
        }

        return true;
    }

    public User tokenGetUser(String token) throws Exception {
        if (validateToken(token)) {
            try {
                return userRepository.getUserByID(Integer.parseInt(getClaims(token).getSubject()));
            } catch (Exception e) {
                return null;
            }
        }
        throw new Exception("Invalid user");
    }

    public String generate(User user) {
        if (user == null)
            return "";

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long milliseconds = System.currentTimeMillis();
        Date now = new Date(milliseconds);

        Key key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId((tokenCounter++).toString())
                .setIssuedAt(now)
                .setSubject(Integer.toString(user.getId()))
                .setIssuer("user")
                .signWith(signatureAlgorithm, key);

        if (sessionMaxTime > 0) {
            builder.setExpiration(new Date(milliseconds + sessionMaxTime));
        }


        String token = builder.compact();
        user.setCurrentToken(token);
        userRepository.setUser(user);

        return token;
    }
}
