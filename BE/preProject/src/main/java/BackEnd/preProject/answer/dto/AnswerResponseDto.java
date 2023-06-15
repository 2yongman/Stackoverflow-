package BackEnd.preProject.answer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {
    private long answerId;
    private long questionId;
    private long memberId;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AnswerResponseDto(long answerId, long questionId, long memberId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.answerId=answerId;
        this.questionId=questionId;
        this.memberId=memberId;
        this.content=content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
