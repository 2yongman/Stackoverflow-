package BackEnd.preProject.tag.entity;

import BackEnd.preProject.question.entity.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long tagId;

    @Setter
    @Column(nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag",orphanRemoval = true)
    private Set<QuestionTag> questionTags = new HashSet<>();

    public Tag(String tagName){
        this.tagName = tagName;
    }
}
