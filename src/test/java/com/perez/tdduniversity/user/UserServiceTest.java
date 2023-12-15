package com.perez.tdduniversity.user;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.perez.tdduniversity.user.UserNotFoundException;
import com.perez.tdduniversity.user.User;
import com.perez.tdduniversity.user.UserRepository;
import com.perez.tdduniversity.system.Constants;
import com.perez.tdduniversity.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setRoles("ROLE_USER ROLE_ADMIN");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setRoles("ROLE_USER");

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("user3");
        user3.setPassword("password3");
        user3.setRoles("ROLE_USER");

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldFindAllUsersSuccess() {
        // Given
        given(userRepository.findAll()).willReturn(users);

        // When
        List<User> foundUsers = userService.findAll();

        // Then
        assertEquals(users.size(), foundUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void itShouldFindUserById() {
        // Given
        Long userId = 1L;
        User userExpected = users.get(0);
        given(userRepository.findById(userId)).willReturn(Optional.of(users.get(0)));

        // When
        User foundUser = userService.findById(userId);

        // Then
        assertEquals(userExpected.getId(), foundUser.getId());
        assertEquals(userExpected.getUsername(), foundUser.getUsername());
        assertEquals(userExpected.getRoles(), foundUser.getRoles());

        //verify if userRepository.findById() was called
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void itShouldFailIfUserDoesNotExistById() {
        // Given
        Long userId = 100L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // When
        Throwable throwable = catchThrowable(() -> userService.findById(userId));

        // Then
        assertTrue(throwable instanceof UserNotFoundException);
        assertEquals(String.format(Constants.USER_NOT_FOUND, userId), throwable.getMessage());
    }
}