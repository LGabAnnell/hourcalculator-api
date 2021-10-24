package ch.gab.hourcalculator.api.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TimeUpdateListRequest {

    @DateTimeFormat(pattern = "HH:mm")
    private List<LocalTime> times;

    private String userToken;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
