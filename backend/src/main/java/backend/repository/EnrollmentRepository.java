package backend.repository;

import backend.entity.Enrollment;
import backend.entity.User;
import backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    List<Enrollment> findByUser(User user);
}
