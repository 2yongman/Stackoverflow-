package BackEnd.preProject.question.service;


import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }
    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question){
        Question findQuestion = findQuestionById(question.getQuestionId());
        findQuestion.setTitle(question.getTitle());
        findQuestion.setContent(question.getContent());
        return questionRepository.save(findQuestion);
    }


    public Question findQuestionById(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

    public void deleteQuestionById(long questionId){
        questionRepository.delete(findQuestionById(questionId));
    }


    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }
}
