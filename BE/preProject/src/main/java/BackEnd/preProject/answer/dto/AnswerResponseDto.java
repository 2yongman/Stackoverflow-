package BackEnd.preProject.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDto {
    private long answerId;
    private long questionId;
    private long memberId;
    private String content;

    public AnswerResponseDto(long answerId, long questionId, long memberId, String content){
        this.answerId=answerId;
        this.questionId=questionId;
        this.memberId=memberId;
        this.content=content;
    }
}
