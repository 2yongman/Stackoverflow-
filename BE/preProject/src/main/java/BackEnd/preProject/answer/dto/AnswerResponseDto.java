package BackEnd.preProject.answer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {
    private String nickname;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AnswerResponseDto(String nickname,String content, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.nickname = nickname;
        this.content=content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
