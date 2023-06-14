package BackEnd.preProject.member.mapper;

import BackEnd.preProject.member.dto.MemberResponseDto;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member signupDtoToMember(SignupDto signupDto){
        return new Member(
                signupDto.getUsername(),
                signupDto.getEmail(),
                signupDto.getNickname(),
                signupDto.getPassword()
        );
    }

    public MemberResponseDto memberToMemberResponseDto(Member member){
        return new MemberResponseDto(
                member.getUsername(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt());
    }


}
