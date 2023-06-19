package BackEnd.preProject.question.dto;


import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionPostDto extends BaseEntity {

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 10)
    private String content;

}
