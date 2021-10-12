package ch.gab.hourcalculator.api.service.api;

import ch.gab.hourcalculator.api.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User getUserByUserName(String userName);

    void createUser(User user) throws Exception;

    User findUserByUserToken(String userToken);
}
