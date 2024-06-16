package com.assignment.securityConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JWTTokenHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //    public static final long JWT_TOKEN_VALIDITY =  60;
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
//    private String secret="!#vcvxvxvxvx#fsvxxvs#BabalukumarPratap!";
//    public String generateToken(UserDetails user){
//        return Jwts
//                .builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//
//    public String generateRefreshToken(Map<String,Object> extractClaims, String subject) {
//        return Jwts
//                .builder()
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24*7))
//                .signWith(getSignKey(),SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private <T> T extractClaims(String token , Function<Claims,T> claimsResolvers){
//        final Claims claims=extractAllClaims(token);
//        return claimsResolvers.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignKey() {
//        byte[] key = Decoders.BASE64.decode("secrete-key");
//        return Keys.hmacShaKeyFor(key);
//    }
//
//
//    public String extractUserName(String token) {
//        return extractClaims(token,Claims::getSubject);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String username=extractUserName(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpires(token));
//    }
//    private boolean isTokenExpires(String token) {
//        return extractClaims(token, Claims::getExpiration).before(new Date());
//    }
//    public String getToken(HttpServletRequest request){
//        String authHeader=getAuthHeaderFromHeader(request);
//        if(authHeader!=null && authHeader.startsWith("Bearer ")){
//            return authHeader.substring(7);
//        }return null;
//    }
//
//    private String getAuthHeaderFromHeader(HttpServletRequest request) {
//        String header=request.getHeader("Authorization");
//        log.info(header);
//        return request.getHeader("Authorization");
//    }
}
