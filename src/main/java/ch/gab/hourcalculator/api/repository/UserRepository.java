package ch.gab.hourcalculator.api.repository;

import ch.gab.hourcalculator.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUserToken(String userToken);
}
