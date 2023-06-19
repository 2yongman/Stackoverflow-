package BackEnd.preProject.question.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class QuestionResponseDto {

    private long questionId;
    private long memberId;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public QuestionResponseDto(long questionId, long memberId,
                               String title, String content,
                               LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.questionId = questionId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
