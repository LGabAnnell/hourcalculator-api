package ch.gab.hourcalculator.api.model.converter;

import ch.gab.hourcalculator.api.model.dto.ClockInOutDto;
import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import ch.gab.hourcalculator.api.model.entity.ClockInOut;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockInOutConverter {
    public static ClockInOutDto fromEntity(ClockInOut entity) {
        return ClockInOutDto.builder()
            .date(entity.getDate())
            .time(entity.getTime())
            .id(entity.getId())
            .build();
    }
}
