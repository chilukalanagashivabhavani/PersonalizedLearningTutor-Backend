package backend.repository;

import backend.entity.QuizAttempt;
import backend.entity.User;
import backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByUser(User user);

    List<QuizAttempt> findByUserAndQuiz(
            User user,
            Quiz quiz);
}
