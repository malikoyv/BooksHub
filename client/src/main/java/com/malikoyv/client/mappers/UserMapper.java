package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.UserDto;
import com.malikoyv.core.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements IMapper<UserDto, User> {
    @Override
    public User map(UserDto userDto) {
        return map(userDto, new User());
    }

    @Override
    public User map(UserDto userDto, User user) {
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        return user;
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
