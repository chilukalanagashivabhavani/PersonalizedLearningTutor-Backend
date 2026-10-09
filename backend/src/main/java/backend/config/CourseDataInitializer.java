package backend.config;

import backend.entity.Course;
import backend.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseDataInitializer {

    @Bean
    CommandLineRunner createCourses(CourseRepository courseRepository) {

        return args -> {

            if (courseRepository.count() == 0) {

                courseRepository.save(
                    new Course(
                        "Machine Learning",
                        "Learn supervised and unsupervised machine learning algorithms.",
                        "Intermediate",
                        "Artificial Intelligence"
                    )
                );

                courseRepository.save(
                    new Course(
                        "Deep Learning",
                        "Learn neural networks, CNNs, RNNs, LSTM and modern deep learning concepts.",
                        "Advanced",
                        "Artificial Intelligence"
                    )
                );

                courseRepository.save(
                    new Course(
                        "Python Programming",
                        "Learn Python programming from fundamentals to practical applications.",
                        "Beginner",
                        "Programming"
                    )
                );

                courseRepository.save(
                    new Course(
                        "Data Structures",
                        "Learn arrays, linked lists, stacks, queues, trees and graphs.",
                        "Intermediate",
                        "Computer Science"
                    )
                );

                courseRepository.save(
                    new Course(
                        "Artificial Intelligence",
                        "Understand AI concepts, search algorithms, knowledge representation and intelligent agents.",
                        "Advanced",
                        "Artificial Intelligence"
                    )
                );

                System.out.println("=================================");
                System.out.println("Sample courses created!");
                System.out.println("=================================");
            }
        };
    }
}
