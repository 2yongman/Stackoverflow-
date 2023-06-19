package BackEnd.preProject.security.controller;


import BackEnd.preProject.security.dto.LoginDto;
import BackEnd.preProject.security.jwt.JwtTokenizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity getLogin(@Valid @RequestBody LoginDto loginDto) throws Exception{
        //Todo Service 로직
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
