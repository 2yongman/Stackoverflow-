package BackEnd.preProject.answer.mapper;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerMapper {
    public Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto) {
        return new Answer(answerPostDto.getContent());
    }
    public Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto) {
        return new Answer(answerPatchDto.getContent());
    }

    public AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        return new AnswerResponseDto(
                answer.getMember().getNickname(),
                answer.getContent(),
                answer.getCreatedAt(),
                answer.getModifiedAt()
                );
    }

    public List<AnswerResponseDto> answersToAnswerResponseDtos(List<Answer> answers){
        List<AnswerResponseDto> list = new ArrayList<>(answers.size());
        for (Answer answer : answers){
            list.add(this.answerToAnswerResponseDto(answer));
        }
        return list;
    }
}
