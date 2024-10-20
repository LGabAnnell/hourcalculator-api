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
import java.util.Optional;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final String KEY;

    public AuthorizationFilter(AuthenticationManager authenticationManager, String KEY) {
        super(authenticationManager);
        this.KEY = KEY;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {

        String token = Optional.ofNullable(request.getCookies())
                .flatMap(r ->
                        Arrays.stream(r)
                                .filter(cookie -> cookie.getName().equals("my-cookie"))
                                .map(Cookie::getValue).findFirst()
                )
                .orElse(null);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(token);

        if (authentication == null) {
            Cookie cookie = new Cookie("my-cookie", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(String token) {
        if (token != null && token.length() > 0) {
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
