package com.perez.tdduniversity.course;

import com.perez.tdduniversity.course.exceptions.CourseAlreadyExistsException;
import com.perez.tdduniversity.course.exceptions.CourseNotFoundException;
import com.perez.tdduniversity.student.Student;
import com.perez.tdduniversity.system.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class CourseServiceTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseService underTest;

    List<Course> courses;
    List<Student> students;

    @BeforeEach
    void setUp() {
        courses = List.of(
                new Course("12345", "Java Programming", "Learn Java Programming", 3, 4),
                new Course("67890", "Python Programming", "Learn Python Programming", 3, 4)
        );

        students = List.of(
                new Student(1, "John Doe", "john@example.com", "1234567890", "123 Main St, Anytown, USA"),
                new Student(2, "Angela Doe", "angela@example.com", "1234567890", "Downton Abbey, Anytown, USA")
        );

        courses.get(0).getStudents().add(students.get(0));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldFindByIdSuccess() {
        // Given
        Course course = courses.get(0);
        given(courseRepository.findById("12345")).willReturn(Optional.of(course));

        // When
        Course courseReturned = underTest.findById("12345");

        // Then
        // Validate each field of the returned course
        assertEquals(course.getCourseId(), courseReturned.getCourseId());
        assertEquals(course.getName(), courseReturned.getName());
        assertEquals(course.getDescription(), courseReturned.getDescription());
        assertEquals(course.getCredits(), courseReturned.getCredits());
        assertEquals(course.getWeeklyHours(), courseReturned.getWeeklyHours());

        // Validate the list of students by comparing the size of the list
        assertEquals(course.getStudents().size(), courseReturned.getStudents().size());

        // Validate if CourseRepository.findById() was called
        verify(courseRepository, times(1)).findById("12345");
    }

    @Test
    void itShouldFailIfCourseDoesNotExistById() {
        // Given
        String courseId = "0101010";
        given(courseRepository.findById(anyString())).willReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(() -> underTest.findById(courseId));
        // Then
        assertTrue(throwable instanceof CourseNotFoundException);
        assertEquals(String.format(Constants.COURSE_NOT_FOUND, courseId), throwable.getMessage());
    }

    @Test
    void itShouldFindAllCoursesSuccess() {
        // Given
        given(courseRepository.findAll()).willReturn(courses);

        // When
        List<Course> coursesReturned = underTest.findAll();

        // Then
        assertEquals(courses.size(), coursesReturned.size());
        assertEquals(courses.get(0).getCourseId(), coursesReturned.get(0).getCourseId());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void itShouldCreateACourseSuccess() {
        // Given
        Course newCourse = new Course("24680", "C# Programming", "Learn C# Programming", 3, 4);
        given(courseRepository.save(newCourse)).willReturn(newCourse);

        // When
        Course courseReturned = underTest.save(newCourse);

        // Then
        assertEquals(newCourse.getCourseId(), courseReturned.getCourseId());
        assertEquals(newCourse.getName(), courseReturned.getName());
        verify(courseRepository, times(1)).save(newCourse);
    }

    @Test
    void itShouldFailWhenSaveIfCourseWithThatIdAlreadyExists() {
        // Given
        Course newCourse = new Course("12345", "Java Programming", "Learn Java Programming", 3, 4);
        given(courseRepository.save(newCourse)).willThrow(new CourseAlreadyExistsException(newCourse.getCourseId()));

        // When
        Throwable throwable = catchThrowable(() -> underTest.save(newCourse));

        // Then
        assertTrue(throwable instanceof CourseAlreadyExistsException);
        assertEquals(String.format(Constants.COURSE_ALREADY_EXISTS, newCourse.getCourseId()), throwable.getMessage());
    }
}