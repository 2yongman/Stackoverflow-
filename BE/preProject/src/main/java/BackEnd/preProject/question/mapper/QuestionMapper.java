package BackEnd.preProject.question.mapper;


import BackEnd.preProject.question.dto.QuestionPatchDto;
import BackEnd.preProject.question.dto.QuestionPostDto;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.question.entity.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {

    public Question questionPostDtoToQuestion(QuestionPostDto questionPostDto){
        return new Question(
                questionPostDto.getTitle(),
                questionPostDto.getContent());

    }

    public Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto){
        return new Question(
                questionPatchDto.getTitle(),
                questionPatchDto.getContent());
    }

    public QuestionResponseDto questionToQuestionResponseDto(Question question){
        return new QuestionResponseDto(
                question.getQuestionId(),
                question.getMember().getUsername(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt(),
                question.getModifiedAt());
    }

    public List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions){
        List<QuestionResponseDto> list = new ArrayList<>(questions.size());
        for (Question question : questions){
            list.add(this.questionToQuestionResponseDto(question));
        }
        return list;
    }

}
