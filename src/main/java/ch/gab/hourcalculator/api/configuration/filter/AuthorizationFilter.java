package ch.gab.hourcalculator.api.configuration.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final String KEY;

    private AuthenticationManager authenticationManager;

    public AuthorizationFilter(AuthenticationManager authenticationManager, String KEY) {
        super(authenticationManager);
        this.KEY = KEY;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse respone,
                                 FilterChain chain) throws IOException, ServletException {

        String token = request.getCookies() != null ? Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("my-cookie"))
                .map(Cookie::getValue).findFirst().orElse(null) : null;

        if (token == null) {
            chain.doFilter(request, respone);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, respone);
    }

    private UsernamePasswordAuthenticationToken authenticate(String token) {
        if (token != null) {
            Claims user = Jwts.parser()
                    .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            if (user.getExpiration().toInstant().isBefore(Instant.now())) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;
    }

}
