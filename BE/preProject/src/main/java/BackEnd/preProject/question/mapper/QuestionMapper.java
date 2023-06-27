package BackEnd.preProject.question.mapper;


import BackEnd.preProject.question.dto.QuestionPatchDto;
import BackEnd.preProject.question.dto.QuestionPostDto;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.tag.entity.QuestionTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<String> strings = new HashSet<>();
        Set<QuestionTag> questionTags = question.getQuestionTagList();
        for (QuestionTag questionTag : questionTags) {
            strings.add(questionTag.getTag().getTagName());
        }
        return new QuestionResponseDto(
                question.getQuestionId(),
                question.getMember().getUsername(),
                question.getTitle(),
                question.getContent(),
                strings,
                question.getCreatedAt(),
                question.getModifiedAt(),
                question.getMember().getNickname());
    }

    public List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions){
        List<QuestionResponseDto> list = new ArrayList<>(questions.size());
        for (Question question : questions){
            list.add(this.questionToQuestionResponseDto(question));
        }
        return list;
    }

}
