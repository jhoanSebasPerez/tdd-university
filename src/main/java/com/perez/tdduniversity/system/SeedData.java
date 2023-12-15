package com.perez.tdduniversity.system;

import com.perez.tdduniversity.course.Course;
import com.perez.tdduniversity.student.Student;
import com.perez.tdduniversity.user.User;
import com.perez.tdduniversity.student.StudentRepository;
import com.perez.tdduniversity.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class SeedData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public SeedData(StudentRepository studentRepository,
                    UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    private void initCoursesAndStudents(){
        Course java = new Course();
        java.setCourseId("java101");
        java.setName("Java 101");
        java.setDescription("Java for beginners");
        java.setCredits(3);
        java.setWeeklyHours(4);

        Course python = new Course();
        python.setCourseId("python101");
        python.setName("Python 101");
        python.setDescription("Python for beginners");
        python.setCredits(3);
        python.setWeeklyHours(4);

        Course webProgramming = new Course();
        webProgramming.setCourseId("web101");
        webProgramming.setName("Web Programming");
        webProgramming.setDescription("Web Programming for beginners");
        webProgramming.setCredits(3);
        webProgramming.setWeeklyHours(4);


        Student student1 = new Student();
        student1.setName("Jhoan Perez");
        student1.setEmail("jhoan@example.com");
        student1.setPhone("1234567890");
        student1.setAddress("123 Main St, Anytown USA");
        student1.addCourse(java);
        student1.addCourse(python);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("John Doe");
        student2.setEmail("doe@exampl.com");
        student2.setPhone("273482321");
        student2.setAddress("123 Main St, Anytown USA");
        student2.addCourse(webProgramming);
        studentRepository.save(student2);
    }

    private void initUsers(){
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setRoles("admin");
        user1.setEnabled(true);
        userService.save(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setRoles("student");
        user2.setEnabled(true);
        userService.save(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("user3");
        user3.setPassword("password3");
        user3.setRoles("student");
        user3.setEnabled(false);
        userService.save(user3);

    }

    @Override
    public void run(String... args) throws Exception {
        initCoursesAndStudents();
        initUsers();
    }
}
