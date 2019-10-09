package com.akohsin.photostorage.security;

import com.akohsin.photostorage.dto.AuthorizationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/**
 * AKN 3/9/18
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private TokenGenerator tokenGenerator;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            AuthorizationDto creds = new ObjectMapper().readValue(req.getInputStream(), AuthorizationDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) throws IOException {
        String username = ((User) auth.getPrincipal()).getUsername();

        String token = tokenGenerator.generate(req, username);
        res.addHeader(SecurityConstants.HEADER_STRING, token);
        res.addHeader(SecurityConstants.EXPIRATION, System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME + "");
        res.addHeader("Access-Control-Expose-Headers", SecurityConstants.HEADER_STRING + ", " + SecurityConstants.EXPIRATION);
    }


}
