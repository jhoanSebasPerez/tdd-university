package com.perez.tdduniversity.student;

import com.perez.tdduniversity.course.Course;
import com.perez.tdduniversity.course.CourseRepository;
import com.perez.tdduniversity.course.exceptions.CourseNotFoundException;
import com.perez.tdduniversity.student.exceptions.StudentNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student save(Student student){
        return studentRepository.save(student);
    }

    public void enrollCourse(Integer idStudent, String courseId){
        Student student = studentRepository.findById(idStudent)
                .orElseThrow(() -> new StudentNotFoundException(idStudent));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        student.addCourse(course);
    }
}
