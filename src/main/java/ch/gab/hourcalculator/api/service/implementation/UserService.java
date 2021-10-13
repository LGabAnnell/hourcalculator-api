package ch.gab.hourcalculator.api.service.implementation;

import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.repository.UserRepository;
import ch.gab.hourcalculator.api.service.api.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repo;

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
            throw new Exception("User already exists");
        }
        UUID uuid = UUID.randomUUID();
        // user.setPassword(pwEncoder.encode(user.getPassword()));
        user.setUserToken(RandomString.make(10));
        System.out.println(uuid.toString());
        user.setUserToken(uuid.toString());
        repo.save(user);
    }


    @Override
    public User findUserByUserToken(String userToken) {
        return repo.findUserByUserToken(userToken);
    }
}
