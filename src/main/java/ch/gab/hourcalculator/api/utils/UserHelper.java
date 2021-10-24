package ch.gab.hourcalculator.api.utils;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
    private UserHelper() {}

    public static String getUserName() {
        return ((DefaultClaims) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getSubject();
    }
}
