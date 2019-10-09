package com.akohsin.photostorage.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenGenerator {

    public String generate(HttpServletRequest req, String username) {
        return SecurityConstants.TOKEN_PREFIX +Jwts.builder()
                .setSubject(RequestTranslator.getRemoteIpFrom(req))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .setIssuer(username)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
    }

    public String parseIp(HttpServletRequest req) {
        String token = req.getHeader(SecurityConstants.HEADER_STRING);
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

    public String parseEmail(HttpServletRequest req) {
        String token = req.getHeader(SecurityConstants.HEADER_STRING);
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody()
                .getIssuer();
    }
}
