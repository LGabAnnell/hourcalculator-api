package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.converter.UserConverter;
import ch.gab.hourcalculator.api.model.dto.UserDetails;
import ch.gab.hourcalculator.api.model.dto.UserDto;
import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.service.api.IUserService;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

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
