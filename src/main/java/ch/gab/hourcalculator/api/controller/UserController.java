package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.converter.UserConverter;
import ch.gab.hourcalculator.api.model.dto.UserDto;
import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.service.api.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("new")
    public ResponseEntity<Void> newUser(@RequestBody User user) throws Exception {
        userService.createUser(user);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public UserDto getUserByName(@RequestParam("userName") String userName) {
        return UserConverter.fromEntity(userService.getUserByUserName(userName));
    }
}
