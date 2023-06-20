package BackEnd.preProject.answer.service;

import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.mapper.AnswerMapper;
import BackEnd.preProject.answer.repository.AnswerRepository;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.repository.QuestionRepository;
import BackEnd.preProject.question.service.QuestionService;
import BackEnd.preProject.response.CursorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final MemberService memberService;
    private final QuestionRepository questionRepository;
    private final AnswerMapper answerMapper;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService, MemberService memberService,QuestionRepository questionRepository,
                         AnswerMapper answerMapper){
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.memberService = memberService;
        this.questionRepository = questionRepository;
        this.answerMapper = answerMapper;
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

        Member member = memberService.getMember(findanswer.getMember().getMemberId(),username); // 글쓴이
        questionService.findQuestionById(findanswer.getQuestion().getQuestionId());

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

    public void deleteAnswerById(long answerId, String username) {
        Answer findAnswer = findAnswerById(answerId);
        Member member = memberService.getMember(findAnswer.getMember().getMemberId(),username);
        String requester = username;
        String creator = member.getUsername();
        if (requester == creator) {
            answerRepository.delete(findAnswer);
        }
    }

    public CursorResult<AnswerResponseDto> infinityScroll(Long questionId, Long cursorId, PageRequest page){
        Question findQuestion = questionService.findQuestionById(questionId);

        List<Answer> answers = getAnswers(findQuestion,cursorId,page);
        List<AnswerResponseDto> answerResponseDtos = answerMapper.answersToAnswerResponseDtos(answers);

        Long lastAnswerIdOfList = answers.isEmpty() ?
                null : answers.get(answers.size() -1).getAnswerId();

        return new CursorResult<>(answerResponseDtos, hasNext(lastAnswerIdOfList));
    }

    private List<Answer> getAnswers(Question question, Long cursorId, PageRequest page){
        return cursorId == null ?
                answerRepository.findAllByQuestionOrderByAnswerIdDesc(question,page) :
                answerRepository.findByQuestionAndAnswerIdLessThanOrderByAnswerIdDesc(question,cursorId,page);
    }

    private Boolean hasNext(Long cursorId){
        if (cursorId == null) return false;
        return this.answerRepository.existsByAnswerIdLessThan(cursorId);
    }
}
