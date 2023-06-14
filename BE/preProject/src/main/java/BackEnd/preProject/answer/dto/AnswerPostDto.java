package BackEnd.preProject.answer.dto;

import BackEnd.preProject.audit.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPostDto extends BaseEntity{
    private long answerId;
    private long questionId;
    private long memberId;
    private String content;


}