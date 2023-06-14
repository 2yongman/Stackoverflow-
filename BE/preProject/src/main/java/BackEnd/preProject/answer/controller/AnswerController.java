package BackEnd.preProject.answer.controller;

import BackEnd.preProject.answer.dto.AnswerPatchDto;
import BackEnd.preProject.answer.dto.AnswerPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    /* answer는 GET이 없음 **/


    //POST
    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerPostDto answerPostDto){

        return new ResponseEntity<AnswerPostDto>(answerPostDto, HttpStatus.CREATED);
    }

    // PATCH
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") long answerId,
                                      @RequestBody AnswerPatchDto answerPatchDto){
        return new ResponseEntity<>(HttpStatus.ACCEPTED) ;
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") long answerId){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}