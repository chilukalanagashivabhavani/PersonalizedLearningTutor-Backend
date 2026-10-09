package backend.repository;

import backend.entity.LessonCompletion;
import backend.entity.Lesson;
import backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonCompletionRepository
        extends JpaRepository<LessonCompletion, Long> {

    Optional<LessonCompletion> findByUserAndLesson(
            User user,
            Lesson lesson);

    List<LessonCompletion> findByUser(User user);
}
