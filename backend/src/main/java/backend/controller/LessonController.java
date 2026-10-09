package backend.controller;

import backend.entity.Course;
import backend.entity.Enrollment;
import backend.entity.Lesson;
import backend.entity.LessonCompletion;
import backend.entity.User;

import backend.repository.CourseRepository;
import backend.repository.EnrollmentRepository;
import backend.repository.LessonCompletionRepository;
import backend.repository.LessonRepository;
import backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonCompletionRepository completionRepository;

    public LessonController(
            LessonRepository lessonRepository,
            CourseRepository courseRepository,
            UserRepository userRepository,
            EnrollmentRepository enrollmentRepository,
            LessonCompletionRepository completionRepository) {

        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.completionRepository = completionRepository;
    }

    @GetMapping("/course/{courseId}")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLessons(
            @PathVariable Long courseId,
            Authentication authentication) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        User user = getUser(authentication);

        List<Lesson> lessons =
                lessonRepository.findByCourseOrderByLessonOrder(course);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Lesson lesson : lessons) {

            Map<String, Object> data = new HashMap<>();

            data.put("id", lesson.getId());
            data.put("title", lesson.getTitle());
            data.put("content", lesson.getContent());
            data.put("lessonOrder", lesson.getLessonOrder());

            boolean completed = completionRepository
                    .findByUserAndLesson(user, lesson)
                    .isPresent();

            data.put("completed", completed);

            result.add(data);
        }

        return result;
    }

    @PostMapping("/{lessonId}/complete")
    @Transactional
    public Map<String, Object> completeLesson(
            @PathVariable Long lessonId,
            Authentication authentication) {

        User user = getUser(authentication);

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException("Lesson not found"));

        Course course = lesson.getCourse();

        Enrollment enrollment =
                enrollmentRepository
                        .findByUserAndCourse(user, course)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Please enroll in this course first"));

        if (completionRepository
                .findByUserAndLesson(user, lesson)
                .isEmpty()) {

            LessonCompletion completion =
                    new LessonCompletion(user, lesson);

            completionRepository.save(completion);
        }

        List<Lesson> lessons =
                lessonRepository
                        .findByCourseOrderByLessonOrder(course);

        int totalLessons = lessons.size();

        int completedLessons = 0;

        for (Lesson currentLesson : lessons) {

            if (completionRepository
                    .findByUserAndLesson(user, currentLesson)
                    .isPresent()) {

                completedLessons++;
            }
        }

        int progress = 0;

        if (totalLessons > 0) {
            progress = (int) Math.round(
                    completedLessons * 100.0 / totalLessons
            );
        }

        enrollment.setProgress(progress);
        enrollmentRepository.save(enrollment);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "Lesson completed successfully");
        response.put("progress", progress);
        response.put("completedLessons", completedLessons);
        response.put("totalLessons", totalLessons);

        return response;
    }

    private User getUser(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("Please login first");
        }

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}
