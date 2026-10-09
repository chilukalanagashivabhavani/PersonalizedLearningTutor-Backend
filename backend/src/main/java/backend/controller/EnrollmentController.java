package backend.controller;

import backend.entity.Course;
import backend.entity.Enrollment;
import backend.entity.User;
import backend.repository.CourseRepository;
import backend.repository.EnrollmentRepository;
import backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/{courseId}")
    @Transactional
    public String enroll(
            @PathVariable Long courseId,
            Authentication authentication) {

        if (authentication == null) {
            return "Please login first";
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository
                .findByUserAndCourse(user, course)
                .isPresent()) {

            return "Already enrolled in this course";
        }

        Enrollment enrollment = new Enrollment(user, course);

        enrollmentRepository.save(enrollment);

        return "Successfully enrolled in " + course.getTitle();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getMyCourses(
            Authentication authentication) {

        if (authentication == null) {
            return new ArrayList<>();
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Map<String, Object>> result = new ArrayList<>();

        for (Enrollment enrollment :
                enrollmentRepository.findByUser(user)) {

            Map<String, Object> courseData = new HashMap<>();

            courseData.put(
                    "enrollmentId",
                    enrollment.getId()
            );

            courseData.put(
                    "courseId",
                    enrollment.getCourse().getId()
            );

            courseData.put(
                    "title",
                    enrollment.getCourse().getTitle()
            );

            courseData.put(
                    "description",
                    enrollment.getCourse().getDescription()
            );

            courseData.put(
                    "difficulty",
                    enrollment.getCourse().getDifficulty()
            );

            courseData.put(
                    "category",
                    enrollment.getCourse().getCategory()
            );

            courseData.put(
                    "progress",
                    enrollment.getProgress()
            );

            result.add(courseData);
        }

        return result;
    }
}
