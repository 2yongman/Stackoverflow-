package BackEnd.preProject.answer.controller;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import BackEnd.preProject.answer.dto.AnswerResponseDto;
import BackEnd.preProject.answer.entity.Answer;
import BackEnd.preProject.answer.mapper.AnswerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerMapper mapper;

    public AnswerController(AnswerMapper answerMapper){
        this.mapper = answerMapper;
    }
    /* answer는 GET이 없음 **/


    //POST
    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerPostDto answerPostDto){
        Answer answer = mapper.answerPostDtoToAnswer(answerPostDto);

        //Todo 여기에 서비스
        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(answer);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.CREATED);
    }

    // PATCH
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @RequestBody AnswerPatchDto answerPatchDto){
        Answer answer = mapper.answerPatchDtoToAnswer(answerPatchDto);
        answer.setAnswerId(answerId);


        //Todo 여기에 서비스

        AnswerResponseDto answerResponseDto = mapper.answerToAnswerResponseDto(answer);
        return new ResponseEntity<>(answerResponseDto, HttpStatus.ACCEPTED) ;
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId){

        //Todo 여기에 서비스
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}