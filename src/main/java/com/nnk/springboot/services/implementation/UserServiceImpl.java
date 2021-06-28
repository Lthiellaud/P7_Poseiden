package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new User.
     * @param user the User to be created
     */
    @Override
    public void createUser(User user) throws UserAlreadyExistException {
        if (getUserByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exists for this email");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Updates a user.
     * @param user the user to be updated
     * @param id id of the user to be updated
     */
    @Override
    public void updateUser(User user, Integer id) {
        user.setId(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Gives all the users
     * @return all the users
     */
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * returns a user from an id
     * @param id the id
     * @return the user
     */
    @Override
    public User getUserById(Integer id) throws IllegalArgumentException {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }

    /**
     * delete a user from an id
     * @param id the id
     */
    @Override
    public void deleteUser(Integer id) throws IllegalArgumentException {
        userRepository.delete(getUserById(id));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}