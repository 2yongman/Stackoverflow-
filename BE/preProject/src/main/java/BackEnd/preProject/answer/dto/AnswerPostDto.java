package BackEnd.preProject.answer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPostDto{
    private long questionId;
    private long memberId;
    private String content;


}