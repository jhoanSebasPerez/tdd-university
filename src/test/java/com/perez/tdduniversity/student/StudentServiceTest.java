package com.perez.tdduniversity.student;

import com.perez.tdduniversity.course.Course;
import com.perez.tdduniversity.course.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    StudentService underTest;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldSaveStudentSuccess() {
        // Given
        Student student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setEmail("john@exmaple.com");
        student.setPhone("1234567890");
        student.setAddress("123 Main St, Anytown, USA");

        given(studentRepository.save(student)).willReturn(student);

        // When
        Student savedStudent = underTest.save(student);

        // Then
        assertEquals(student.getId(), savedStudent.getId());
        assertEquals(student.getName(), savedStudent.getName());
        assertEquals(student.getEmail(), savedStudent.getEmail());
        assertEquals(student.getPhone(), savedStudent.getPhone());
        assertEquals(student.getAddress(), savedStudent.getAddress());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void itShouldEnrollACourseToSpecificStudent() {
        // Given
        Course course = new Course("12345", "Java Programming", "Learn Java Programming", 3, 4);

        Student student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setEmail("j@example.com");
        student.setPhone("1234567890");
        student.setAddress("123 Main St, Anytown, USA");

        given(studentRepository.findById(1)).willReturn(Optional.of(student));
        given(courseRepository.findById("12345")).willReturn(Optional.of(course));
        //given(studentRepository.save(student)).willReturn(student);

        // When
        underTest.enrollCourse(1, "12345");

        // Then
        assertEquals(1, student.getCourses().size());
        assertEquals("12345", student.getCourses().get(0).getCourseId());
        assertTrue(student.getCourses().contains(course));
        verify(studentRepository, times(1)).findById(1);
        verify(courseRepository, times(1)).findById("12345");
    }
}