package BackEnd.preProject.question.service;


import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question, String username){
        Member findMember = memberService.findMemberByUsername(username);
        question.setMember(findMember);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question, String username){
        Question findQuestion = findQuestionById(question.getQuestionId());
        Member member = memberService.findMemberByUsername(username); // 검증목적

        if (findQuestion.getMember().getUsername() == member.getUsername()){
            findQuestion.setTitle(question.getTitle());
            findQuestion.setContent(question.getContent());
            return questionRepository.save(findQuestion);
        }
        else {
            throw new IllegalArgumentException("작성자 본인이 아닙니다.");
        }
    }


    public Question findQuestionById(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

    public void deleteQuestionById(long questionId, String username){
        Question findQuestion = findQuestionById(questionId);
        Member member = memberService.findMemberByUsername(username); // 검증목적

        if (findQuestion.getMember().getUsername() == member.getUsername()){
            questionRepository.delete(findQuestionById(questionId));
        }
        else {
            throw new IllegalArgumentException("작성자 본인이 아닙니다.");
        }
    }


    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }

    //infinitiy scroll
    public Page<Question> questionInfinityScroll(Long questionId, int size){
        PageRequest pageRequest = PageRequest.of(0,size);
        return questionRepository.findByQuestionIdLessThanOrderByQuestionIdDesc(questionId,pageRequest);
    }
}
