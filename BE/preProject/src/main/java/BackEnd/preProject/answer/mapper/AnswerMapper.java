package BackEnd.preProject.answer.mapper;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {
    public Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto) {
        return new Answer(answerPostDto.getQuestionId(),
                answerPostDto.getMemberId(),
                answerPostDto.getContent());
    }
    public Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto) {
        return new Answer(answerPatchDto.getQuestionId(),
                answerPatchDto.getMemberId(),
                answerPatchDto.getContent());
    }

    public AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        return new AnswerResponseDto(answer.getAnswerId(),
                answer.getQuestionId(),
                answer.getMemberId(),
                answer.getContent());
    }
}