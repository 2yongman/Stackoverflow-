package BackEnd.preProject.member.controller;

import BackEnd.preProject.member.dto.MemberResponseDto;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.mapper.MemberMapper;
import BackEnd.preProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody SignupDto signupDto){
        signupDto.checkPasswordMatch();
        Member member = memberMapper.signupDtoToMember(signupDto);
        Member saveMember = memberService.signup(member);
        return new ResponseEntity<MemberResponseDto>(memberMapper.memberToMemberResponseDto(saveMember), HttpStatus.CREATED);
    }
}

