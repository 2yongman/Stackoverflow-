package BackEnd.preProject.answer.controller;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.mapper.AnswerMapper;
import BackEnd.preProject.answer.service.AnswerService;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.response.CursorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/answers")
@Validated
public class AnswerController {

    private static final int DEFAULT_SIZE = 10;

    private final AnswerMapper mapper;
    private final AnswerService service;

    public AnswerController(AnswerMapper answerMapper, AnswerService answerService) {
        this.mapper = answerMapper;
        this.service = answerService;
    }
    /* answer는 GET이 없음 **/


    //POST
    @PostMapping("/{question-id}")
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto,
                                     @PathVariable("question-id") @Positive long questionId,
                                     Authentication authentication) {
        String username = authentication.getName();

        Answer answer = mapper.answerPostDtoToAnswer(answerPostDto);
        Answer serviceResult = service.createAnswer(answer, username, questionId);
        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(serviceResult);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.CREATED);
    }

    // PATCH
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto,
                                      Authentication authentication) {
        String username = authentication.getName();

        Answer answer = mapper.answerPatchDtoToAnswer(answerPatchDto);
        answer.setAnswerId(answerId);
        Answer serviceResult = service.updateAnswer(answer, username);
        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(serviceResult);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId,
                                       Authentication authentication) {
        String username = authentication.getName();
        service.deleteAnswerById(answerId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/infinity/{question-id}")
    public ResponseEntity answerInfinityScroll(@Positive @PathVariable("question-id") Long questionId,
                                               @RequestParam(required = false) Long cursorId,
                                               @RequestParam(required = false) Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        CursorResult<AnswerResponseDto> cursorResult = service.infinityScroll(questionId, cursorId, PageRequest.of(0, size));
        return new ResponseEntity(cursorResult, HttpStatus.OK);
    }

    @PostMapping("/{question-id}/answer/{answer-id}/select")
    public ResponseEntity selectAnswer(@Positive @PathVariable("question-id") Long questionId,
                                       @Positive @PathVariable("answer-id") Long answerId,
                                       Authentication authentication){
        String username = authentication.getName();
        service.selectAnswer(questionId,answerId,username);
        return new ResponseEntity(HttpStatus.OK);
    }

}