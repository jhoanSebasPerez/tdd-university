package com.perez.tdduniversity.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perez.tdduniversity.course.converters.CourseToCourseResponseDto;
import com.perez.tdduniversity.course.dtos.CourseRequestDto;
import com.perez.tdduniversity.course.exceptions.CourseAlreadyExistsException;
import com.perez.tdduniversity.course.exceptions.CourseNotFoundException;
import com.perez.tdduniversity.student.Student;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WithMockUser(username = "user1", password = "password1", roles = "admin") //Set admin user to test the endpoints
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class CourseControllerTest {

    @MockBean
    CourseService courseService;

    @Autowired
    MockMvc mockMvc;

    List<Course> courses;

    @Value("${api.endpoint.base-url}") // Injects the value of the property api.endpoint.base-url from application.yml
    String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CourseToCourseResponseDto courseToCourseResponseDto;

    @BeforeEach
    void setUp() {
        courses = new ArrayList<>();

        Course course1 = new Course();
        course1.setCourseId("12345");
        course1.setName("Java Programming");
        course1.setDescription("Learn Java Programming");
        course1.setCredits(3);
        course1.setWeeklyHours(4);
        courses.add(course1);

        Course course2 = new Course();
        course2.setCourseId("67890");
        course2.setName("Python Programming");
        course2.setDescription("Learn Python Programming");
        course2.setCredits(3);
        course2.setWeeklyHours(4);
        courses.add(course2);

        Course course3 = new Course();
        course3.setCourseId("13579");
        course3.setName("C# Programming");
        course3.setDescription("Learn C# Programming");
        course3.setCredits(3);
        course3.setWeeklyHours(4);
        courses.add(course3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldFindCourseById() throws Exception {
        // Given
        given(courseService.findById("12345")).willReturn(courses.get(0));

        // When and Then
        mockMvc.perform(get(baseUrl + "/courses/12345").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.COURSE_FIND_ONE_SUCCESS))
                .andExpect(jsonPath("$.data.courseId").value("12345"))
                .andExpect(jsonPath("$.data.name").value("Java Programming"));
    }

    @Test
    void itShouldFailWhenNotFoundCourseById() throws Exception {
        // Given
        String courseId = "010101";
        given(courseService.findById("010101")).willThrow(new CourseNotFoundException(courseId));

        // When and Then
        mockMvc.perform(get(baseUrl + "/courses/" + courseId).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(String.format(Constants.COURSE_NOT_FOUND, courseId)));
    }

    @Test
    void itShouldCreateANewCourseSuccessWithPOSTRequest() throws Exception {
        // Given
        List<Student> students = List.of();
        Course newCourse = new Course("24009", "Operating System", "Learn OS concepts", 3, 4, students);
        given(courseService.save(Mockito.any(Course.class))).willReturn(newCourse);

        //Convert Course to CourseRequestDto to send in the request body
        CourseRequestDto courseRequestDto = new CourseRequestDto(
                newCourse.getCourseId(), newCourse.getName(), newCourse.getDescription(), newCourse.getCredits(), newCourse.getWeeklyHours());

        // When & Then
        mockMvc.perform(post(baseUrl + "/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(Constants.COURSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.data.courseId").value("24009"))
                .andExpect(jsonPath("$.data.name").value("Operating System"));

    }

    @Test
    void itShouldReturnBadRequestIfCourseAlreadyExistWithIdWhenSave() throws Exception {
        // Given
        Course newCourse = new Course("12345", "Java Programming", "Learn Java Programming", 3, 4);
        given(courseService.save(Mockito.any(Course.class))).willThrow(new CourseAlreadyExistsException(newCourse.getCourseId()));

        //Convert Course to CourseRequestDto to send in the request body
        CourseRequestDto courseRequestDto = new CourseRequestDto(
                newCourse.getCourseId(), newCourse.getName(), newCourse.getDescription(), newCourse.getCredits(), newCourse.getWeeklyHours());

        // When & Then
        mockMvc.perform(post(baseUrl + "/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto)))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data").value(String.format(Constants.COURSE_ALREADY_EXISTS, newCourse.getCourseId())));
    }
}