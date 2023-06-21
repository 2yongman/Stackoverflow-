package BackEnd.preProject.member.controller;

import BackEnd.preProject.member.dto.MemberPatchDto;
import BackEnd.preProject.member.dto.MemberPatchResponseDto;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.dto.SignupResponseDto;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.mapper.MemberMapper;
import BackEnd.preProject.member.service.MemberService;
import BackEnd.preProject.response.MultiResponseDto;
import BackEnd.preProject.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.xml.stream.Location;
import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


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
        //Todo header에 location 추가
        URI location = UriCreator.createUri("/signup", saveMember.getMemberId());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", String.valueOf(location));
        return new ResponseEntity<SignupResponseDto>(memberMapper.memberToSignupResponseDto(saveMember),headers, HttpStatus.CREATED);
    }

    @PatchMapping("/members/{member-id}")
    public ResponseEntity<MemberPatchResponseDto> update(@Positive @PathVariable("member-id") Long memberId,
                                                         @Valid @RequestBody MemberPatchDto memberPatchDto,
                                                         Authentication authentication){

            String username = authentication.getName();

            Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
            Member updateMember = memberService.update(memberId,member,username);
            URI location = UriCreator.createUri("/members", updateMember.getMemberId());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Location", String.valueOf(location));
            return new ResponseEntity<MemberPatchResponseDto>(memberMapper.memberToMemberPatchResponseDto(updateMember),HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}")
    public ResponseEntity<SignupResponseDto> getMember(@Positive @PathVariable("member-id") Long memberId,
                                                       Authentication authentication){
        String username = authentication.getName();
        Member findMember = memberService.getMember(memberId, username);
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
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId,
                                       Authentication authentication){
        String username = authentication.getName();
        memberService.deleteMember(memberId,username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/verify")
    public String checkDuplicateUsername(@RequestParam String username){
        memberService.checkDuplicateUsername(username);
        return "사용가능한 " + username + " 입니다.";
    }
}

