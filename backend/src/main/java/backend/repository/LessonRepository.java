package backend.repository;

import backend.entity.Lesson;
import backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseOrderByLessonOrder(Course course);
}
