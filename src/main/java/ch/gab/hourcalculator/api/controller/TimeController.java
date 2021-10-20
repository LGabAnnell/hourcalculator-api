package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.dto.ClockInOutDto;
import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import ch.gab.hourcalculator.api.service.api.IUserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
public class TimeController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<String> getTimes() {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping("/post-time")
    public ResponseEntity<Void> postTime(@RequestBody TimeRequest timeRequest) {
        System.out.println(timeRequest.toString());
        userService.insertUserClock(timeRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/test-auth")
    public ResponseEntity<Void> testAuth() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/user-clocks-by-date")
    public ResponseEntity<List<ClockInOutDto>> getUserClocksByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        String username = ((DefaultClaims) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getSubject();
        return ResponseEntity.ok(userService.getUserClocksByDate(username, date));
    }

    @GetMapping("/user-clocks")
    public ResponseEntity<List<ClockInOutDto>> getUserClocks() {
        String username = ((DefaultClaims) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getSubject();
        return ResponseEntity.ok(userService.getUserClocks(username));
    }
}
