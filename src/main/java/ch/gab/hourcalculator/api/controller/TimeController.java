package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.dto.*;
import ch.gab.hourcalculator.api.service.api.IUserService;
import ch.gab.hourcalculator.api.utils.UserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TimeController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<String> getTimes() {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping("post-time")
    public ResponseEntity<Void> postTime(@RequestBody TimeRequest timeRequest) {
        userService.insertUserClock(timeRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("test-auth")
    public ResponseEntity<Void> testAuth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("user-clocks-by-date")
    public ResponseEntity<List<ClockInOutDto>> getUserClocksByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        String username = UserHelper.getUserName();
        return ResponseEntity.ok(userService.getUserClocksByDate(username, date));
    }

    @GetMapping("user-clocks")
    public ResponseEntity<List<ClockInOutDto>> getUserClocks() {
        String username = UserHelper.getUserName();
        return ResponseEntity.ok(userService.getUserClocks(username));
    }

    @PutMapping("update-time")
    public ResponseEntity<Void> updateUserClock(@RequestBody TimeUpdateRequest timeRequest) throws Exception {
        userService.updateUserClock(timeRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-times")
    public ResponseEntity<Void> updateUserClocks(@RequestBody TimeUpdateListRequest timeRequest) throws Exception {
        userService.updateUserClocks(timeRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("week")
    public ResponseEntity<WeeklyClocksDto> getWholeWeek(@RequestParam Integer week) {
        return ResponseEntity.ok(userService.getClocksByWeek(week));
    }
}
