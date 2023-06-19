package BackEnd.preProject.question.entity;


import BackEnd.preProject.audit.BaseEntity;
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

    @Column(nullable = false, updatable = false)
    private long memberId;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Question(long memberId,
                    String title,
                    String content){
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

}
