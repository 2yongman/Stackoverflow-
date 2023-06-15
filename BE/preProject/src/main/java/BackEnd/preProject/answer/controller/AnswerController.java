package BackEnd.preProject.answer.controller;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.mapper.AnswerMapper;
import BackEnd.preProject.answer.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/answers")
@Validated
public class AnswerController {
    private final AnswerMapper mapper;
    private final AnswerService service;

    public AnswerController(AnswerMapper answerMapper, AnswerService answerService){
        this.mapper = answerMapper;
        this.service = answerService;
    }
    /* answer는 GET이 없음 **/


    //POST
    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto){
        Answer answer = mapper.answerPostDtoToAnswer(answerPostDto);
        Answer serviceResult = service.createAnswer(answer);
        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(serviceResult);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.CREATED);
    }

    // PATCH
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto){
        Answer answer = mapper.answerPatchDtoToAnswer(answerPatchDto);
        answer.setAnswerId(answerId);
        Answer serviceResult = service.updateAnswer(answer);
        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(serviceResult);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.ACCEPTED) ;
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId){
        service.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}