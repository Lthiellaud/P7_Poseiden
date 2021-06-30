package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() throws Exception {
        User user = new User();
        user.setUsername("NewUser");
        user.setFullname("New User");
        user.setPassword("NewUser?1");
        user.setRole("USER");

        userService.createUser(user);

        assertThat(userService.getUserById(3).getUsername()).isEqualTo("NewUser");
        assertThat(userService.getUserById(3).getFullname()).isEqualTo("New User");
        assertThat(userService.getUserById(3).getRole()).isEqualTo("USER");

    }

    @Test
    public void updateUserTest() throws Exception {
        User user = new User();
        user.setUsername("User Updated");
        user.setFullname("Updated User");
        user.setPassword("UpdatedUser?1");
        user.setRole("USER");

        userService.updateUser(user, 1);

        assertThat(userService.getUserById(1).getUsername()).isEqualTo("User Updated");
        assertThat(userService.getUserById(1).getFullname()).isEqualTo("Updated User");
        assertThat(userService.getUserById(1).getRole()).isEqualTo("USER");


    }

    @Test
    public void deleteUserTest() throws Exception {
        assertThat(userService.getUserById(2).getUsername()).isEqualTo("user");

        userService.deleteUser(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> userService.getUserById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid user Id: 2");

    }
}
