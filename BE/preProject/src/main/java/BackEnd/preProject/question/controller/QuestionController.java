package BackEnd.preProject.question.controller;


import BackEnd.preProject.question.dto.QuestionPatchDto;
import BackEnd.preProject.question.dto.QuestionPostDto;
import BackEnd.preProject.question.dto.QuestionResponseDto;
import BackEnd.preProject.question.entity.Question;
import BackEnd.preProject.question.mapper.QuestionMapper;
import BackEnd.preProject.question.service.QuestionService;
import BackEnd.preProject.security.jwt.JwtTokenizer;
import BackEnd.preProject.utils.JwtDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/questions")
@RestController
public class QuestionController {
    private final QuestionMapper mapper;
    private final QuestionService service;

    public QuestionController(QuestionMapper mapper, QuestionService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity postQuestion(Authentication authentication,
                                       @Valid @RequestBody QuestionPostDto questionPostDto) {

        String username = authentication.getName();

        Question question = mapper.questionPostDtoToQuestion(questionPostDto);
        Question serviceResult = service.createQuestion(question, username);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@Valid @RequestBody QuestionPatchDto questionPatchDto,
                                        @PathVariable("question-id") @Positive long questionId,
                                        Authentication authentication) {
        String username = authentication.getName();


        Question question = mapper.questionPatchDtoToQuestion(questionPatchDto);
        question.setQuestionId(questionId);
        Question serviceResult = service.updateQuestion(question, username);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam(defaultValue = "0") String page,
                                       @RequestParam(defaultValue = "0") String size) {

        //Todo page 구현
        Page<Question> serviceResult = service.findQuestions(Integer.parseInt(page),
                Integer.parseInt(size));
        List<Question> questions = serviceResult.getContent();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") long questionId) {
        Question serviceResult = service.findQuestionById(questionId);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);
        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") long questionId,
                                         Authentication authentication) {
        String username = authentication.getName();
        service.deleteQuestionById(questionId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
