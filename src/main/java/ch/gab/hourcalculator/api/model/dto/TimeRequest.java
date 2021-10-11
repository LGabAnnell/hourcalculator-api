package ch.gab.hourcalculator.api.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimeRequest {
    private String date;
    private String time;
    private String userToken;
}
