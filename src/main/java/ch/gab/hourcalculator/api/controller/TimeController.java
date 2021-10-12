package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class TimeController {
    @GetMapping
    public ResponseEntity<String> getTimes() {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping("/post-time")
    public ResponseEntity<Void> postTime(@RequestBody TimeRequest timeRequest) {
        return ResponseEntity.ok(null);
    }
}
