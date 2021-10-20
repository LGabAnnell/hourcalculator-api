package ch.gab.hourcalculator.api.service.api;

import ch.gab.hourcalculator.api.model.dto.ClockInOutDto;
import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import ch.gab.hourcalculator.api.model.entity.ClockInOut;
import ch.gab.hourcalculator.api.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface IUserService extends UserDetailsService {
    User getUserByUserName(String userName);

    void createUser(User user) throws Exception;

    User findUserByUserToken(String userToken);

    List<ClockInOutDto> getUserClocks(String username);

    void insertUserClock(TimeRequest request);

    List<ClockInOutDto> getUserClocksByDate(String username, LocalDate date);

    void updateUserClock(TimeRequest request) throws Exception;
}
