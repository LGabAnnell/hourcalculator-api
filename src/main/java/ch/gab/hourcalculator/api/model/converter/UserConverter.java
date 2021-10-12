package ch.gab.hourcalculator.api.model.converter;

import ch.gab.hourcalculator.api.model.dto.UserDto;
import ch.gab.hourcalculator.api.model.entity.User;

public class UserConverter {
    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .userName(user.getUsername())
                .userToken(user.getUserToken()).build();
    }
}
