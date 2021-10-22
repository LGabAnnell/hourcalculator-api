package ch.gab.hourcalculator.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CLOCK_IN_OUT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClockInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CLOCK_DATE")
    private LocalDate date;

    @Column(name = "CLOCK_TIME")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "USER_TOKEN", referencedColumnName = "USER_TOKEN")
    private User user;
}
