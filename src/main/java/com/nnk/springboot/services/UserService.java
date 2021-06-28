package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.UserAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user) throws UserAlreadyExistException;
    void updateUser(User user, Integer id);
    List<User> getAllUser();
    User getUserById(Integer id);
    void deleteUser(Integer id);
    Optional<User> getUserByUsername(String username);
}
