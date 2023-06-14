package BackEnd.preProject.answer.dto;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPatchDto extends BaseEntity {
    long answerId;
    String content;
}
