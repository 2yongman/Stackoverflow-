package BackEnd.preProject.answer.dto;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AnswerPatchDto extends BaseEntity {
    @NotNull
    @PositiveOrZero
    private long answerId;
    private long questionId;
    private long memberId;

    @NotBlank
    @Size(min = 5)
    private String content;
}
