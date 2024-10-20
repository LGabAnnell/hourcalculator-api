package ch.gab.hourcalculator.api.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClockInOutDto {
    private LocalDate date;
    private LocalTime time;
    private Long id;

    public Object toLocalDate() {
        return date;
    }
}
