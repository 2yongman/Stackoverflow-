package BackEnd.preProject.member.service;

import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member){
        memberExistCheck(member.getUsername());
        return memberRepository.save(member);
    }

    private void memberExistCheck(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXIST);
        }
    }
}
