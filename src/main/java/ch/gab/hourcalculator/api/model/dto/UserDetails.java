package ch.gab.hourcalculator.api.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDetails {
    String sub;
    Long exp;
}
