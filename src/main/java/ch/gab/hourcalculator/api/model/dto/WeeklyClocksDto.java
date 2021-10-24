package ch.gab.hourcalculator.api.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class WeeklyClocksDto {
    private Map<LocalDate, ClockInOutDto> weeklyClocks = new HashMap<>();
}
