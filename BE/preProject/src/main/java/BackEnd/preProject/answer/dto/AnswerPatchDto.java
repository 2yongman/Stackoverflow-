package BackEnd.preProject.answer.dto;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerPatchDto extends BaseEntity {
    private long answerId;
    private long questionId;
    private long memberId;
    private String content;
    //Todo Valid 추가해야함
}
