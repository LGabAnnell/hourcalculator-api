package ch.gab.hourcalculator.api.repository;

import ch.gab.hourcalculator.api.model.entity.ClockInOut;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClockInOutRepository extends JpaRepository<ClockInOut, Long> {
    List<ClockInOut> findClockInOutByUserUserToken(String userToken);
}
