package backend.controller;

import backend.entity.Course;
import backend.entity.Enrollment;
import backend.entity.Quiz;
import backend.entity.QuizAttempt;
import backend.entity.User;

import backend.repository.CourseRepository;
import backend.repository.EnrollmentRepository;
import backend.repository.QuizAttemptRepository;
import backend.repository.QuizRepository;
import backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuizRepository quizRepository;
    private final QuizAttemptRepository attemptRepository;
    private final UserRepository userRepository;

    public RecommendationController(
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository,
            QuizRepository quizRepository,
            QuizAttemptRepository attemptRepository,
            UserRepository userRepository) {

        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.quizRepository = quizRepository;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getRecommendations(
            Authentication authentication) {

        User user = getUser(authentication);

        List<Enrollment> enrollments =
                enrollmentRepository.findByUser(user);

        Set<Long> enrolledCourseIds =
                new HashSet<>();

        Map<String, Integer> categoryScores =
                new HashMap<>();

        for (Enrollment enrollment : enrollments) {

            Course course = enrollment.getCourse();

            enrolledCourseIds.add(course.getId());

            int progress = enrollment.getProgress();

            int score = categoryScores.getOrDefault(
                    course.getCategory(), 0);

            categoryScores.put(
                    course.getCategory(),
                    score + progress
            );
        }

        List<QuizAttempt> attempts =
                attemptRepository.findByUser(user);

        for (QuizAttempt attempt : attempts) {

            Quiz quiz = attempt.getQuiz();

            Course course = quiz.getCourse();

            int percentage = 0;

            if (attempt.getTotalQuestions() > 0) {

                percentage =
                        (int) Math.round(
                            attempt.getScore() * 100.0 /
                            attempt.getTotalQuestions()
                        );
            }

            int score =
                    categoryScores.getOrDefault(
                            course.getCategory(), 0);

            categoryScores.put(
                    course.getCategory(),
                    score + percentage
            );
        }

        List<Map<String, Object>> recommendations =
                new ArrayList<>();

        for (Course course : courseRepository.findAll()) {

            if (enrolledCourseIds.contains(course.getId())) {
                continue;
            }

            int score =
                    categoryScores.getOrDefault(
                            course.getCategory(), 0);

            int recommendationScore = 0;

            String reason;

            if (score >= 150) {

                recommendationScore = 95;

                reason =
                    "Recommended because you have shown strong "
                    + "interest and performance in "
                    + course.getCategory() + ".";

            } else if (score >= 80) {

                recommendationScore = 80;

                reason =
                    "Recommended based on your learning activity "
                    + "in " + course.getCategory() + ".";

            } else {

                recommendationScore = 60;

                reason =
                    "Recommended as a useful next course to "
                    + "expand your learning skills.";
            }

            Map<String, Object> data =
                    new HashMap<>();

            data.put("id", course.getId());
            data.put("title", course.getTitle());
            data.put("description",
                    course.getDescription());
            data.put("difficulty",
                    course.getDifficulty());
            data.put("category",
                    course.getCategory());
            data.put("score",
                    recommendationScore);
            data.put("reason", reason);

            recommendations.add(data);
        }

        recommendations.sort(
            (a, b) ->
                Integer.compare(
                    (Integer) b.get("score"),
                    (Integer) a.get("score")
                )
        );

        return recommendations;
    }

    private User getUser(Authentication authentication) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Please login first");
        }

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }
}
