package com.malikoyv.api.services;

import com.malikoyv.client.mappers.UserMapper;
import com.malikoyv.client.contract.UserDto;
import com.malikoyv.core.model.User;
import com.malikoyv.core.repositories.ICatalogData;
import com.malikoyv.core.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ICatalogData db;
    private final UserMapper userMapper;

    public UserService(ICatalogData db, UserMapper userMapper) {
        this.db = db;
        this.userMapper = userMapper;
    }

    public long createUser(UserDto dto) {
        User user = userMapper.map(dto);
        db.getUsers().save(user);
        return user.getId();
    }

    public UserDto getUser(long id) {
        User user = db.getUsers().findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return db.getUsers().findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateUser(long id, UserDto dto) {
        User user = db.getUsers().findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.map(dto, user);
        db.getUsers().save(user);
    }

    public void deleteUser(long id) {
        db.getUsers().deleteById(id);
    }
}

