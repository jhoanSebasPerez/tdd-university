package com.perez.tdduniversity.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perez.tdduniversity.course.dtos.CourseRequestDto;
import com.perez.tdduniversity.system.Constants;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Course Controller Integration Tests")
@Tag("integration")
@ActiveProfiles("dev")
class CourseControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        // Make POST request to /users/login to get a token
        // Login as user with admin Role
        ResultActions resultActions = mockMvc.perform(post(baseUrl + "/auth/login")
                .with(httpBasic("user1", "password1")));

        MvcResult result = resultActions
                //.andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        token = jsonObject.getJSONObject("data").getString("token");
    }

    @Test
    void itShouldFindCourseByIdIfTokenIsSent() throws Exception {
        mockMvc.perform(get(baseUrl + "/courses/java101")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.COURSE_FIND_ONE_SUCCESS))
                .andExpect(jsonPath("$.data.courseId").value("java101"))
                .andExpect(jsonPath("$.data.students").isNotEmpty())
                .andExpect(jsonPath("$.data.students[0].id").value("1"));
    }

    @Test
    void itShouldCreateCourseAndThenValidateIfWasPersisted() throws Exception{
        CourseRequestDto courseRequestDto = new CourseRequestDto
                ("algo101", "Algorithms", "Learn Algorithms fundamentals", 3, 4);

        // Make a POST request to /courses to create a new course
        mockMvc.perform(post(baseUrl + "/courses")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(Constants.COURSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.data.courseId").value(courseRequestDto.courseId()))
                .andExpect(jsonPath("$.data.name").value(courseRequestDto.name()));

        // Make a GET request to /courses/algo101 to validate if the course was persisted
        mockMvc.perform(get(baseUrl + "/courses/" + courseRequestDto.courseId())
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.COURSE_FIND_ONE_SUCCESS))
                .andExpect(jsonPath("$.data.courseId").value(courseRequestDto.courseId()))
                .andExpect(jsonPath("$.data.name").value(courseRequestDto.name()));
    }

    @Test
    void itShouldFailIfTryCreateACourseWithStudentRole() throws Exception{
        // Given
        // Login as user with student Role
        ResultActions resultActions = mockMvc.perform(post(baseUrl + "/auth/login")
                .with(httpBasic("user2", "password2")));

        String content = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        String studentToken = jsonObject.getJSONObject("data").getString("token");


        // When & Then
        // Make a POST request to /courses to create a new course, and fail because user has student role
        CourseRequestDto courseRequestDto = new CourseRequestDto
                ("algo101", "Algorithms", "Learn Algorithms fundamentals", 3, 4);
        mockMvc.perform(post(baseUrl + "/courses")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("$.message").value(Constants.USER_HAS_NOT_PERMISSION));


    }
}
