package backend.controller;

import backend.entity.User;
import backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (user.getUsername() == null ||
                user.getUsername().isBlank()) {

            return "Username is required";
        }

        if (user.getPassword() == null ||
                user.getPassword().length() < 6) {

            return "Password must contain at least 6 characters";
        }

        if (userRepository
                .findByUsername(user.getUsername())
                .isPresent()) {

            return "Username already exists";
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        // Public registration can ONLY create students.
        user.setRole("STUDENT");

        userRepository.save(user);

        return "User registered successfully";
    }
}
