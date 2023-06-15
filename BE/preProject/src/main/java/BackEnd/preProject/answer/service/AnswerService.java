package BackEnd.preProject.answer.service;

import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.repository.AnswerRepository;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        long targetAnswerId = answer.getAnswerId();
        Answer findAnswer = findAnswerById(targetAnswerId);
        findAnswer.setContent(answer.getContent());
        return answerRepository.save(findAnswer);
    }

    public Answer findAnswerById(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }

    public void deleteAnswerById(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        answerRepository.delete(findAnswer);
    }
}
