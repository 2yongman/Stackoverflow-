package BackEnd.preProject.question.service;


import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.mapper.QuestionMapper;
import BackEnd.preProject.question.repository.QuestionRepository;
import BackEnd.preProject.response.CursorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
        this.questionMapper = questionMapper;
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



    public List<Question> findQuestions(int page, int size) {
        Page<Question> questions = questionRepository.findAll(PageRequest.of(page, size,

                Sort.by("questionId").descending()));

        List<Question> questionList = questions.getContent();
        List<Question> newReply = new ArrayList<>(questionList.size());
        for (Question question:questionList){
            newReply.add(question);
        }
        return newReply;
    }

    //infinitiy scroll
    public CursorResult<QuestionResponseDto> getInfinityQuestion(Long cursorId, Pageable pageable){
        final List<Question> questions = getQuestions(cursorId,pageable);
        final List<QuestionResponseDto> questionResponseDtos = questionMapper.questionsToQuestionResponseDtos(questions);

        final Long lastIdOfList = questions.isEmpty() ?
                null : questions.get(questions.size() - 1).getQuestionId();

        return new CursorResult<>(questionResponseDtos, hasNext(lastIdOfList));
    }

    //search
    @Transactional
    public List<Question> search(String keyward){
        return questionRepository.findByTitleContainingOrderByCreatedAtDesc(keyward);
    }

    private List<Question> getQuestions(Long questionId, Pageable pageable){
        return questionId == null ?
                this.questionRepository.findAllByOrderByQuestionIdDesc(pageable) :
                this.questionRepository.findByQuestionIdLessThanOrderByQuestionIdDesc(questionId,pageable);
    }

    private Boolean hasNext(Long questionId){
        if (questionId == null) return false;
        return this.questionRepository.existsByQuestionIdLessThan(questionId);

    }
}