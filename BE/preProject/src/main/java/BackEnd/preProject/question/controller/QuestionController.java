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
    public ResponseEntity postQuestion(@RequestHeader HttpHeaders headers,
            @Valid @RequestBody QuestionPostDto questionPostDto){



        String accessToken = headers.getFirst("Authorization");
        //Todo accessToken의 내부정보를 print
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(accessToken.split("\\.")[1]));
        Map<String, Object> tokenPayload = new JwtDecoder().jwtPayloadToMap(payload);
        System.out.println(tokenPayload);

        System.out.println("username : "+ tokenPayload.get("username"));
        System.out.println("roles : "+ tokenPayload.get("roles"));




        Question question = mapper.questionPostDtoToQuestion(questionPostDto);
        Question serviceResult = service.createQuestion(question);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@Valid @RequestBody QuestionPatchDto questionPatchDto,
                                        @PathVariable("question-id") @Positive long questionId){
        Question question = mapper.questionPatchDtoToQuestion(questionPatchDto);
        question.setQuestionId(questionId);
        Question serviceResult = service.updateQuestion(question);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam(defaultValue = "0") String page,
                                       @RequestParam(defaultValue = "0") String size){

        //Todo page 구현
        Page<Question> serviceResult = service.findQuestions(Integer.parseInt(page),
                Integer.parseInt(size));
        List<Question> questions = serviceResult.getContent();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") long questionId){
        Question serviceResult = service.findQuestionById(questionId);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(serviceResult);
        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") long questionId){
        service.deleteQuestionById(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
