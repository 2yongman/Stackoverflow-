package BackEnd.preProject.member.controller;

import BackEnd.preProject.member.dto.MemberPatchDto;
import BackEnd.preProject.member.dto.MemberPatchResponseDto;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.dto.SignupResponseDto;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.mapper.MemberMapper;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupDto signupDto){
        signupDto.checkPasswordMatch();
        Member member = memberMapper.signupDtoToMember(signupDto);
        Member saveMember = memberService.signup(member);
        return new ResponseEntity<SignupResponseDto>(memberMapper.memberToSignupResponseDto(saveMember), HttpStatus.CREATED);
    }

    @PatchMapping("/members/{member-id}")
    public ResponseEntity<MemberPatchResponseDto> update(@Positive @PathVariable("member-id") Long memberId,
                                                         @Valid @RequestBody MemberPatchDto memberPatchDto){
            Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
            Member updateMember = memberService.update(memberId,member);
            return new ResponseEntity<MemberPatchResponseDto>(memberMapper.memberToMemberPatchResponseDto(updateMember),HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}")
    public ResponseEntity<SignupResponseDto> getMember(@Positive @PathVariable("member-id") Long memberId){
        Member findMember = memberService.getMember(memberId);
        return new ResponseEntity<SignupResponseDto>(memberMapper.memberToSignupResponseDto(findMember),HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        Page<Member> pageMember = memberService.getMembers(page - 1, size);
        List<Member> members = pageMember.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(memberMapper.membersToSignupResponseDtos(members),pageMember),HttpStatus.OK);
    }

    @DeleteMapping("/members/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/verify")
    public String checkDuplicateUsername(@RequestParam String username){
        memberService.checkDuplicateUsername(username);
        return "사용가능한 " + username + " 입니다.";
    }
}

