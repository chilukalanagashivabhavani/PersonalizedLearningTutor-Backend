package backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private int lessonOrder;

    public Lesson() {
    }

    public Lesson(
            Course course,
            String title,
            String content,
            int lessonOrder) {

        this.course = course;
        this.title = title;
        this.content = content;
        this.lessonOrder = lessonOrder;
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

    public String getContent() {
        return content;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }
}
