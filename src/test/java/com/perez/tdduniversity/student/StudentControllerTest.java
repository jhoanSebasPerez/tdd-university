package com.perez.tdduniversity.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perez.tdduniversity.student.converters.StudentToStudentResponseDto;
import com.perez.tdduniversity.student.dtos.StudentRequestDto;
import com.perez.tdduniversity.system.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(username = "user1", password = "password1", roles = "admin") //Set admin user to test the endpoints
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}") // Injects the value of the property api.endpoint.base-url from application.yml
    String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StudentToStudentResponseDto studentToStudentResponseDto;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldSaveStudentAndReturnStudentWithId() throws Exception{
        // Given
        Student student = new Student();
        student.setId(1);
        student.setName("John");
        student.setEmail("john@example.com");
        student.setPhone("1234567890");
        student.setAddress("123 Main St");
        given(studentService.save(Mockito.any(Student.class))).willReturn(student);

        StudentRequestDto studentRequestDto = new StudentRequestDto("John", "john@example.com", "1234567890", "123 Main St");

        // When & Then
        mockMvc.perform(post(baseUrl + "/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDto)))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(Constants.STUDENT_CREATE_SUCCESS))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }
}