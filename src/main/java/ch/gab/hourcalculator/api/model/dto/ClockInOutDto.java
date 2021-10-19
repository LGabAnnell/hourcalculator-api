package ch.gab.hourcalculator.api.model.dto;

import lombok.*;

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
}
