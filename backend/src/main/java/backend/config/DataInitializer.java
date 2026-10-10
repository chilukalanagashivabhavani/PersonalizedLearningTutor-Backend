package backend.config;

import backend.entity.User;
import backend.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                String initialAdminPassword = System.getenv("ADMIN_INITIAL_PASSWORD");
                if (initialAdminPassword == null || initialAdminPassword.isBlank()) {
                    System.out.println("Default admin account was not created because ADMIN_INITIAL_PASSWORD is not set.");
                    return;
                }

                User user = new User(
                        "admin",
                        passwordEncoder.encode(initialAdminPassword),
                        "ADMIN"
                );
                userRepository.save(user);
                System.out.println("Default admin account created.");
            }
        };
    }
}
