package BackEnd.preProject.question.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
public class QuestionResponseDto {

    private long questionId;
    private String username;
    private String nickname;
    private String title;
    private String content;
    private Set<String> tags;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public QuestionResponseDto(long questionId, String username, String title, String content,Set<String> tags, LocalDateTime createdAt, LocalDateTime modifiedAt,
                               String nickname) {
        this.questionId = questionId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.nickname = nickname;
    }
}
