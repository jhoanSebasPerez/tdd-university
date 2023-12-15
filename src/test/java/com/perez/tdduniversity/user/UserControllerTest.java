package com.perez.tdduniversity.user;

import com.perez.tdduniversity.user.User;
import com.perez.tdduniversity.user.UserService;
import com.perez.tdduniversity.system.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    List<User> users;

    @Value("${api.endpoint.base-url}") // Injects the value of the property api.endpoint.base-url from application.yml
    String baseUrl;

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
    void itShouldFindAll() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldFindById() throws Exception {
        // Given
        given(userService.findById(1L)).willReturn(users.get(0));

        // When and Then
        mockMvc.perform(get(baseUrl+ "/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.USER_FIND_ONE_SUCCESS))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.username").value("user1"));
    }
}