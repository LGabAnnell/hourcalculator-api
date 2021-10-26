package ch.gab.hourcalculator.api.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class WeeklyClocksDto {
    private Map<LocalDate, List<ClockInOutDto>> weeklyClocks = new HashMap<>();
}
