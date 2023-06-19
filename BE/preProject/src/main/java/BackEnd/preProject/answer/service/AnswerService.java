package BackEnd.preProject.answer.service;

import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.repository.AnswerRepository;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    private final MemberService memberService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService, MemberService memberService){
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.memberService = memberService;
    }

    public Answer createAnswer(Answer answer, String username, long questionId) {
        //Todo user : 요청자 // questionid : 답글 달려는 질문 // answer:content
        // 질문이 있는지 봐야함 //
        Question question = questionService.findQuestionById(questionId);
        Member findMember  = memberService.findMemberByUsername(username);
        answer.setQuestion(question);
        answer.setMember(findMember);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer, String username) {
        Answer findanswer = findAnswerById(answer.getAnswerId());

        Member member = memberService.getMember(findanswer.getMember().getMemberId()); // 글쓴이
        Question question = questionService.findQuestionById(findanswer.getQuestion().getQuestionId());

        if (String.valueOf(member.getUsername()).equals(username)){
            long targetAnswerId = answer.getAnswerId();
            Answer findAnswer = findAnswerById(targetAnswerId);
            findAnswer.setContent(answer.getContent());
            return answerRepository.save(findAnswer);
        }

        else throw new IllegalArgumentException("본인이 아닙니다.");

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
