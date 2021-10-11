package ch.gab.hourcalculator.api.service.api;

import ch.gab.hourcalculator.api.model.entity.User;

public interface IUserService {
    User getUserByUserName(String userName);

    void createUser(User user) throws Exception;
}
