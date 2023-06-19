package BackEnd.preProject.question.mapper;


import BackEnd.preProject.question.dto.QuestionPatchDto;
import BackEnd.preProject.question.dto.QuestionPostDto;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.question.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question questionPostDtoToQuestion(QuestionPostDto questionPostDto){
        return new Question(questionPostDto.getMemberId(),
                questionPostDto.getTitle(),
                questionPostDto.getContent());

    }

    public Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto){
        return new Question(questionPatchDto.getMemberId(),
                questionPatchDto.getTitle(),
                questionPatchDto.getContent());
    }

    public QuestionResponseDto questionToQuestionResponseDto(Question question){
        return new QuestionResponseDto(question.getQuestionId(),
                question.getMemberId(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt(),
                question.getModifiedAt());
    }

}
