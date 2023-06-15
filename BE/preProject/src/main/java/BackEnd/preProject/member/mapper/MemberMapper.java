package BackEnd.preProject.member.mapper;

import BackEnd.preProject.member.dto.MemberPatchDto;
import BackEnd.preProject.member.dto.MemberPatchResponseDto;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.dto.SignupResponseDto;
import BackEnd.preProject.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public SignupResponseDto memberToSignupResponseDto(Member member){
        return new SignupResponseDto(
                member.getUsername(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt());
    }

    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto){
        return new Member(
                memberPatchDto.getEmail(),
                memberPatchDto.getNickname(),
                memberPatchDto.getPassword()
        );
    }

    public MemberPatchResponseDto memberToMemberPatchResponseDto(Member member){
        return new MemberPatchResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    public List<SignupResponseDto> membersToSignupResponseDtos(List<Member> members){
        List<SignupResponseDto> list = new ArrayList<>(members.size());
        for (Member member : members){
            list.add(this.memberToSignupResponseDto(member));
        }
        return list;
    }

}
