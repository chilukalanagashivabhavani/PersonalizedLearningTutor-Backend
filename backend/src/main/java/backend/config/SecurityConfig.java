package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                // =========================
                // CSRF
                // =========================
                .csrf(csrf -> csrf.disable())

                // =========================
                // AUTHORIZATION
                // =========================
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC PAGES
                        // =========================
                        .requestMatchers(
                                "/login-page.html",
                                "/register.html",
                                "/register",
                                "/api/auth/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico")
                        .permitAll()

                        // =========================
                        // ADMIN APIs
                        // =========================
                        .requestMatchers(
                                "/api/admin/**")
                        .hasRole("ADMIN")

                        // =========================
                        // ADMIN PAGE
                        // =========================
                        .requestMatchers(
                                "/admin.html")
                        .hasRole("ADMIN")

                        // =========================
                        // AUTHENTICATED PAGES
                        // =========================
                        .requestMatchers(
                                "/dashboard",
                                "/profile",
                                "/profile.html",
                                "/courses.html",
                                "/progress.html",
                                "/practice.html",
                                "/recommendations.html",
                                "/settings.html",
                                "/learn.html")
                        .authenticated()

                        // =========================
                        // COURSE APIs
                        // =========================
                        .requestMatchers(
                                "/api/courses/**",
                                "/api/enrollments/**",
                                "/api/lessons/**",
                                "/api/quizzes/**",
                                "/api/recommendations/**")
                        .authenticated()

                        // =========================
                        // USER APIs
                        // =========================
                        .requestMatchers(
                                "/api/auth/me",
                                "/api/auth/password")
                        .authenticated()

                        // =========================
                        // ALL OTHER REQUESTS
                        // =========================
                        .anyRequest().authenticated())

                // =========================
                // LOGIN
                // =========================
                .formLogin(form -> form
                        .loginPage("/login-page.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll())

                // =========================
                // LOGOUT
                // =========================
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login-page.html?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }

    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
