package BackEnd.preProject.question.dto;


import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
public class QuestionPatchDto extends BaseEntity {

    @NotNull
    private long questionId;
    @NotNull
    private long memberId;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 10)
    private String content;

    private Set<String> tag;

}
