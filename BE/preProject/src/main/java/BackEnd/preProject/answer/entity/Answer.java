package BackEnd.preProject.answer.entity;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;
    @Column(nullable = false, updatable = false) // FK 로 변경해야함
    private long questionId;
    @Column(nullable = false, updatable = false)
    private long memberId;

    @Column(nullable = false)
    private String content;
}
