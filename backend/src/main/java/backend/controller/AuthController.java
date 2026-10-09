package backend.controller;

import backend.entity.User;
import backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==============================
    // CURRENT USER
    // ==============================

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(
            Authentication authentication) {

        User user = getUser(authentication);

        Map<String, Object> response = new HashMap<>();

        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());

        return response;
    }


    // ==============================
    // CHANGE PASSWORD
    // ==============================

    @PutMapping("/password")
    public Map<String, Object> changePassword(
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        User user = getUser(authentication);

        String currentPassword =
                request.get("currentPassword");

        String newPassword =
                request.get("newPassword");


        Map<String, Object> response =
                new HashMap<>();


        if (currentPassword == null ||
                newPassword == null ||
                currentPassword.isBlank() ||
                newPassword.isBlank()) {

            response.put(
                    "message",
                    "Please enter both passwords");

            response.put("success", false);

            return response;
        }


        if (!passwordEncoder.matches(
                currentPassword,
                user.getPassword())) {

            response.put(
                    "message",
                    "Current password is incorrect");

            response.put("success", false);

            return response;
        }


        if (newPassword.length() < 6) {

            response.put(
                    "message",
                    "New password must contain at least 6 characters");

            response.put("success", false);

            return response;
        }


        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);


        response.put(
                "message",
                "Password changed successfully");

        response.put("success", true);

        return response;
    }


    // ==============================
    // GET USER
    // ==============================

    private User getUser(
            Authentication authentication) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Please login first");
        }

        return userRepository
                .findByUsername(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }
}
