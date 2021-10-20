package ch.gab.hourcalculator.api.service.implementation;

import ch.gab.hourcalculator.api.exception.EntityAlreadyExistsException;
import ch.gab.hourcalculator.api.model.converter.ClockInOutConverter;
import ch.gab.hourcalculator.api.model.dto.ClockInOutDto;
import ch.gab.hourcalculator.api.model.dto.TimeRequest;
import ch.gab.hourcalculator.api.model.entity.ClockInOut;
import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.repository.ClockInOutRepository;
import ch.gab.hourcalculator.api.repository.UserRepository;
import ch.gab.hourcalculator.api.service.api.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ClockInOutRepository clockInOutRepository;

    @Override
    public User getUserByUserName(String userName) {
        return repo.findUserByUsername(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repo.findUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .userToken(user.getUserToken())
                .build();
    }

    @Override
    public void createUser(User user) throws Exception {
        if (repo.findUserByUsername(user.getUsername()) != null) {
            throw new EntityAlreadyExistsException("User already exists");
        }

        UUID uuid = UUID.randomUUID();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserToken(RandomString.make(10));
        user.setUserToken(uuid.toString());
        repo.save(user);
    }


    @Override
    public User findUserByUserToken(String userToken) {
        return repo.findUserByUserToken(userToken);
    }

    @Override
    public List<ClockInOutDto> getUserClocks(String username) {
        User user = repo.findUserByUsername(username);
        return clockInOutRepository.findClockInOutByUserUserToken(user.getUserToken()).stream().map(
            ClockInOutConverter::fromEntity
        ).collect(Collectors.toList());
    }

    @Override
    public void insertUserClock(TimeRequest request) {
        User user = repo.findUserByUserToken(request.getUserToken());
        ClockInOut clockInOut = ClockInOut.builder()
            .date(LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("dd.MM.yy")))
            .time(LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH.mm")))
            .user(user)
            .build();

        clockInOutRepository.save(clockInOut);
    }

    @Override
    public List<ClockInOutDto> getUserClocksByDate(String username, LocalDate date) {
        User user = repo.findUserByUsername(username);
        List<ClockInOut> clocks = clockInOutRepository.findAll(Example.of(
            ClockInOut.builder().user(user).date(date).build()
        ));
        return clocks.stream().map(ClockInOutConverter::fromEntity)
            .sorted(Comparator.comparing(ClockInOutDto::getTime)).collect(Collectors.toList());
    }

    @Override
    public void updateUserClock(TimeRequest request) throws Exception {}
}
