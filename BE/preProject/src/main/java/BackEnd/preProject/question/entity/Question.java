package BackEnd.preProject.question.entity;


import BackEnd.preProject.audit.BaseEntity;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.tag.entity.QuestionTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_WAIT;

    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private Set<QuestionTag> questionTagList = new HashSet<>();

    public enum QuestionStatus {
        QUESTION_WAIT("답변 대기"),
        QUESTION_COMPLETE("답변 완료");

        @Getter
        private String questionDescription;

        QuestionStatus(String questionDescription){
            this.questionDescription = questionDescription;
        }
    }

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
