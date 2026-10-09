package backend.controller;

import backend.entity.Course;
import backend.entity.Question;
import backend.entity.Quiz;
import backend.entity.QuizAttempt;
import backend.entity.User;

import backend.repository.CourseRepository;
import backend.repository.QuestionRepository;
import backend.repository.QuizAttemptRepository;
import backend.repository.QuizRepository;
import backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

        private final QuizRepository quizRepository;
        private final QuestionRepository questionRepository;
        private final QuizAttemptRepository attemptRepository;
        private final CourseRepository courseRepository;
        private final UserRepository userRepository;

        public QuizController(
                        QuizRepository quizRepository,
                        QuestionRepository questionRepository,
                        QuizAttemptRepository attemptRepository,
                        CourseRepository courseRepository,
                        UserRepository userRepository) {

                this.quizRepository = quizRepository;
                this.questionRepository = questionRepository;
                this.attemptRepository = attemptRepository;
                this.courseRepository = courseRepository;
                this.userRepository = userRepository;
        }

        @GetMapping("/course/{courseId}")
        @Transactional(readOnly = true)
        public List<Map<String, Object>> getCourseQuizzes(
                        @PathVariable Long courseId) {

                Course course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new RuntimeException("Course not found"));

                List<Map<String, Object>> result = new ArrayList<>();

                for (Quiz quiz : quizRepository.findByCourse(course)) {

                        Map<String, Object> data = new HashMap<>();

                        data.put("id", quiz.getId());
                        data.put("title", quiz.getTitle());
                        data.put("totalQuestions", quiz.getTotalQuestions());

                        result.add(data);
                }

                return result;
        }

        @GetMapping("/{quizId}")
        @Transactional(readOnly = true)
        public Map<String, Object> getQuiz(
                        @PathVariable Long quizId) {

                Quiz quiz = quizRepository.findById(quizId)
                                .orElseThrow(() -> new RuntimeException("Quiz not found"));

                List<Question> questions = questionRepository.findByQuiz(quiz);

                Map<String, Object> result = new HashMap<>();

                result.put("id", quiz.getId());
                result.put("title", quiz.getTitle());
                result.put("totalQuestions", quiz.getTotalQuestions());

                List<Map<String, Object>> questionData = new ArrayList<>();

                for (Question question : questions) {

                        Map<String, Object> data = new HashMap<>();

                        data.put("id", question.getId());
                        data.put("questionText", question.getQuestionText());
                        data.put("optionA", question.getOptionA());
                        data.put("optionB", question.getOptionB());
                        data.put("optionC", question.getOptionC());
                        data.put("optionD", question.getOptionD());

                        questionData.add(data);
                }

                result.put("questions", questionData);

                return result;
        }

        @PostMapping("/{quizId}/submit")
        @Transactional
        public Map<String, Object> submitQuiz(
                        @PathVariable Long quizId,
                        @RequestBody Map<String, String> answers,
                        Authentication authentication) {

                User user = getUser(authentication);

                Quiz quiz = quizRepository.findById(quizId)
                                .orElseThrow(() -> new RuntimeException("Quiz not found"));

                List<Question> questions = questionRepository.findByQuiz(quiz);

                int score = 0;

                for (Question question : questions) {

                        String answer = answers.get(String.valueOf(question.getId()));

                        if (answer != null &&
                                        answer.equalsIgnoreCase(
                                                        question.getCorrectAnswer())) {

                                score++;
                        }
                }

                QuizAttempt attempt = new QuizAttempt(
                                user,
                                quiz,
                                score,
                                questions.size());

                attemptRepository.save(attempt);

                int percentage = questions.isEmpty()
                                ? 0
                                : (int) Math.round(
                                                score * 100.0 / questions.size());

                Map<String, Object> result = new HashMap<>();

                result.put("message", "Quiz submitted successfully");
                result.put("score", score);
                result.put("totalQuestions", questions.size());
                result.put("percentage", percentage);

                return result;
        }

        @GetMapping("/my-attempts")
        @Transactional(readOnly = true)
        public List<Map<String, Object>> getMyAttempts(
                        Authentication authentication) {

                User user = getUser(authentication);

                List<Map<String, Object>> result = new ArrayList<>();

                for (QuizAttempt attempt : attemptRepository.findByUser(user)) {

                        Map<String, Object> data = new HashMap<>();

                        data.put("id", attempt.getId());
                        data.put("quizTitle",
                                        attempt.getQuiz().getTitle());
                        data.put("score", attempt.getScore());
                        data.put("totalQuestions",
                                        attempt.getTotalQuestions());
                        data.put("percentage",
                                        attempt.getTotalQuestions() == 0
                                                        ? 0
                                                        : Math.round(
                                                                        attempt.getScore() * 100.0
                                                                                        / attempt.getTotalQuestions()));
                        data.put("attemptedAt",
                                        attempt.getAttemptedAt());

                        result.add(data);
                }

                return result;
        }

        private User getUser(Authentication authentication) {

                if (authentication == null) {
                        throw new RuntimeException("Please login first");
                }

                return userRepository
                                .findByUsername(authentication.getName())
                                .orElseThrow(() -> new RuntimeException("User not found"));
        }
}
