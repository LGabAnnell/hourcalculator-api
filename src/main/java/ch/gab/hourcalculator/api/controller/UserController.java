package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.service.api.IUserService;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;

    @PostMapping("new")
    public ResponseEntity<Void> newUser(@RequestBody User user) throws Exception {
        userService.createUser(user);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("currentuser")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("token")
    public String userToken() {
        String username = ((DefaultClaims) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getSubject();
        return userService.getUserByUserName(username).getUserToken();
    }
}
