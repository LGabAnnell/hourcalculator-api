package ch.gab.hourcalculator.api.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeUpdateRequest {
    private LocalDate date;
    private LocalTime time;
    private Long id;
}
