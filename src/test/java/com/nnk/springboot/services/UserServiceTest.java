package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.configuration.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static User user;

    @BeforeEach
    public void initTest() {
        user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setFullname("User Test");
        user.setRole("USER");
    }

    @Test
    void createUserTest() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.createUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void tryToCreateAlreadyExistUserTest() throws Exception {
        when(userRepository.findUserByUsername("user")).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void updateUserTest() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.updateUser(user, 1);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User user1 = userService.getUserById(1);

        verify(userRepository, times(1)).findById(1);
        assertThat(user1).isEqualTo(user);
    }

    @Test
    void getUserByIdNotFoundedTest() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> userService.getUserById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid user Id: 1");
        verify(userRepository, times(1)).findById(1);

    }

    @Test
    void deleteUserNotFoundedTest() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> userService.deleteUser(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid user Id: 1");
        verify(userRepository, times(1)).findById(1);

    }
}