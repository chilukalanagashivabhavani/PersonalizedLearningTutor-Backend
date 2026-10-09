package backend.controller;

import backend.entity.Course;
import backend.entity.User;
import backend.repository.CourseRepository;
import backend.repository.EnrollmentRepository;
import backend.repository.QuizAttemptRepository;
import backend.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public AdminController(
            UserRepository userRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository,
            QuizAttemptRepository quizAttemptRepository) {

        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        long students =
                userRepository.findAll()
                        .stream()
                        .filter(u ->
                                "STUDENT".equalsIgnoreCase(
                                        u.getRole()))
                        .count();

        stats.put("students", students);
        stats.put("courses", courseRepository.count());
        stats.put("enrollments",
                enrollmentRepository.count());
        stats.put("quizAttempts",
                quizAttemptRepository.count());

        return stats;
    }

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/students")
    public List<Map<String, Object>> getStudents() {

        List<Map<String, Object>> result =
                new ArrayList<>();

        for (User user : userRepository.findAll()) {

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                continue;
            }

            Map<String, Object> student =
                    new HashMap<>();

            student.put("id", user.getId());
            student.put("username",
                    user.getUsername());
            student.put("role",
                    user.getRole());

            result.add(student);
        }

        return result;
    }
}
