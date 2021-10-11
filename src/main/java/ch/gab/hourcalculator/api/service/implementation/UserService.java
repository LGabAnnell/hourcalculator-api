package ch.gab.hourcalculator.api.service.implementation;

import ch.gab.hourcalculator.api.model.entity.User;
import ch.gab.hourcalculator.api.repository.UserRepository;
import ch.gab.hourcalculator.api.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements
        IUserService {
    @Autowired
    private UserRepository repo;

    @Override
    public User getUserByUserName(String userName) {
        return repo.findUserByUserName(userName);
    }

    @Override
    public void createUser(User user) throws Exception {
        if (repo.findUserByUserName(user.getUserName()) != null) {
            throw new Exception("User already exists");
        }

        repo.save(user);
    }
}
