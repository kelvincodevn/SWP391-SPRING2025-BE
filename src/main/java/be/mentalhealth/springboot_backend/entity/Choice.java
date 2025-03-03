package be.mentalhealth.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "choices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "choice_text", nullable = false, columnDefinition = "TEXT")
    private String choiceText;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference // Ngăn vòng lặp JSON khi serialize
    private Question question;
<<<<<<< HEAD

    // 🔹 Thêm phương thức này để tránh lỗi
    public void setQuestion(Question question) {
        this.question = question;
    }
}
=======
}

>>>>>>> 5982d663ddbb7d04b583f75c1c58e950d57db4e7
