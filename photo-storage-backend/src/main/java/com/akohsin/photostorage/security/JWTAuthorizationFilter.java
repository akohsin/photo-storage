package com.akohsin.photostorage.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * AKN 3/9/18
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenGenerator tokenGenerator;

    public JWTAuthorizationFilter(AuthenticationManager authManager, TokenGenerator tokenGenerator) {
        super(authManager);
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            String authorization = req.getParameter("Authorization");
            if (authorization != null) {
                header = authorization;
            } else {
                chain.doFilter(req, res);
                return;
            }
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {

        String email = tokenGenerator.parseEmail(req);
        String ip = tokenGenerator.parseIp(req);

        if (email != null && RequestTranslator.getRemoteIpFrom(req).equals(ip)) {
            return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        }
        return null;
    }
}
