package BackEnd.preProject.answer.dto;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPostDto extends BaseEntity {

    @NotBlank
    @Size(min = 5)
    private String content;

}