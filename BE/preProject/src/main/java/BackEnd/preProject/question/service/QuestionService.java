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
import BackEnd.preProject.tag.entity.QuestionTag;
import BackEnd.preProject.tag.entity.Tag;
import BackEnd.preProject.tag.repository.QuestionTagRepository;
import BackEnd.preProject.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final QuestionMapper questionMapper;
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService, QuestionMapper questionMapper, TagRepository tagRepository,
                           QuestionTagRepository questionTagRepository) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
        this.questionMapper = questionMapper;
        this.tagRepository = tagRepository;
        this.questionTagRepository = questionTagRepository;
    }

    public Question createQuestion(Question question, String username, Set<String> tags) {
        Set<QuestionTag> questionTags = new HashSet<>();
        if (tags.size() == 0) {
            questionTags = null;
        } else {
            questionTags = createTag(question, tags);
        }
        question.setQuestionTagList(questionTags);

        Member findMember = memberService.findMemberByUsername(username);
        question.setMember(findMember);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question, String username, Set<String> tags) {
        Question findQuestion = findQuestionById(question.getQuestionId());
        Member member = memberService.findMemberByUsername(username); // 검증목적
        if (findQuestion.getMember().getUsername() == member.getUsername()) {
            findQuestion.setTitle(question.getTitle());
            findQuestion.setContent(question.getContent());

            Set<QuestionTag> questionTags = findQuestion.getQuestionTagList();
            Iterator<QuestionTag> iterator = questionTags.iterator();
            Set<String> updatedTags = new HashSet<>(tags);  // 수정된 태그 목록

            while (iterator.hasNext()) {
                QuestionTag questionTag = iterator.next();
                String existingTag = questionTag.getTag().getTagName();

                if (updatedTags.contains(existingTag)) {
                    updatedTags.remove(existingTag);  // 수정된 태그 목록에서 기존 태그 제거
                } else {
                    iterator.remove();  // 수정된 태그 목록에 없는 기존 태그 제거
                }
            }

// 수정된 태그 목록에 새로운 태그 추가
            for (String newTag : updatedTags) {
                QuestionTag questionTag = new QuestionTag();
                questionTag.setQuestion(findQuestion);

                Tag tag = new Tag();
                tag.setTagName(newTag);
                tagRepository.save(tag);

                questionTag.setTag(tag);
                questionTagRepository.save(questionTag);

                questionTags.add(questionTag);

            }

            findQuestion.setQuestionTagList(questionTags);

        } else {
            throw new IllegalArgumentException("작성자 본인이 아닙니다.");
        }
        return questionRepository.save(findQuestion);
    }


    public Question  findQuestionById(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

    public void deleteQuestionById(long questionId, String username) {
        Question findQuestion = findQuestionById(questionId);
        Member member = memberService.findMemberByUsername(username); // 검증목적

        if (findQuestion.getMember().getUsername() == member.getUsername()) {
            questionRepository.delete(findQuestionById(questionId));
        } else {
            throw new IllegalArgumentException("작성자 본인이 아닙니다.");
        }
    }


    public List<Question> findQuestions(int page, int size) {
        Page<Question> questions = questionRepository.findAll(PageRequest.of(page, size,

                Sort.by("questionId").descending()));

        List<Question> questionList = questions.getContent();
        List<Question> newReply = new ArrayList<>(questionList.size());
        for (Question question : questionList) {
            newReply.add(question);
        }
        return newReply;
    }

    //infinitiy scroll
    public CursorResult<QuestionResponseDto> getInfinityQuestion(Long cursorId, Pageable pageable) {
        final List<Question> questions = getQuestions(cursorId, pageable);
        final List<QuestionResponseDto> questionResponseDtos = questionMapper.questionsToQuestionResponseDtos(questions);

        final Long lastIdOfList = questions.isEmpty() ?
                null : questions.get(questions.size() - 1).getQuestionId();

        return new CursorResult<>(questionResponseDtos, hasNext(lastIdOfList));
    }

    //search
    @Transactional
    public List<Question> search(String keyward) {
        return questionRepository.findByTitleContainingOrderByCreatedAtDesc(keyward);
    }

    private List<Question> getQuestions(Long questionId, Pageable pageable) {
        return questionId == null ?
                this.questionRepository.findAllByOrderByQuestionIdDesc(pageable) :
                this.questionRepository.findByQuestionIdLessThanOrderByQuestionIdDesc(questionId, pageable);
    }

    private Boolean hasNext(Long questionId) {
        if (questionId == null) return false;
        return this.questionRepository.existsByQuestionIdLessThan(questionId);

    }

    public List<Question> findQuestionsByUsername(String username) {
        Member findMember = memberService.findMemberByUsername(username); // username이 있는지 확인
        long memberId = findMember.getMemberId();

        List<Question> questions = questionRepository.findByMemberMemberId(memberId);
        return questions;
    }

    //태그 생성
    private Set<QuestionTag> createTag(Question question, Set<String> tags) {
        Set<QuestionTag> questionTags = new HashSet<>();
        for (String tag : tags) {
            Tag newTag = new Tag();
            newTag.setTagName(tag);
            Tag saveTag = tagRepository.save(newTag);

            QuestionTag questionTag = new QuestionTag();
            questionTag.setQuestion(question);
            questionTag.setTag(saveTag);
            questionTagRepository.save(questionTag);
            questionTags.add(questionTag);
        }
        return questionTags;
    }
}