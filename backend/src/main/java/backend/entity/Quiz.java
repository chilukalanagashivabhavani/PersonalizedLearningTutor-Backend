package backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int totalQuestions;

    public Quiz() {
    }

    public Quiz(Course course, String title, int totalQuestions) {
        this.course = course;
        this.title = title;
        this.totalQuestions = totalQuestions;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
