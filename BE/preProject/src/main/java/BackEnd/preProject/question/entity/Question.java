package BackEnd.preProject.question.entity;


import BackEnd.preProject.audit.BaseEntity;
import BackEnd.preProject.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Question(long questionId, Member member, String title, String content) {
        this.questionId = questionId;
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public Question(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
