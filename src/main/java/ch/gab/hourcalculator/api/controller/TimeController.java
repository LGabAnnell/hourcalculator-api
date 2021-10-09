package ch.gab.hourcalculator.api.controller;

import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TimeController {
    @GetMapping(path = "")
    public ResponseEntity<String> getTimes() {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping("/post-time")
    public ResponseEntity<Void> postTime(@RequestBody TimeRequest timeRequest) {
        System.out.println(timeRequest);
        return ResponseEntity.ok(null);
    }
}
