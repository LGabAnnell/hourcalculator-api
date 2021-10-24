package ch.gab.hourcalculator.api.configuration.filter;

import ch.gab.hourcalculator.api.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String KEY;
    private AuthenticationManager authManager;

    public AuthenticationFilter(AuthenticationManager authManager, String KEY) {
        super(authManager);
        this.KEY = KEY;
        this.authManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) {
        Date exp = new Date(System.currentTimeMillis() + 432_000_000L /* five days */);
        Claims claims = Jwts.claims()
                .setSubject(((User) auth.getPrincipal()).getUsername());
        String token = Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,
                        KEY.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp).compact();

        Cookie cookie = new Cookie("my-cookie", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 356 * 50);
        res.addCookie(cookie);
        res.setStatus(200);
    }
}
