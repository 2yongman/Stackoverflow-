package BackEnd.preProject.member.service;

import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member){
        memberExistCheck(member.getUsername());
        return memberRepository.save(member);
    }

    public Member update(Long memberId,Member member){
        Member findMember = verifyMember(memberId);
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> findMember.setNickname(nickname));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password));

        return memberRepository.save(findMember);
    }

    public Member getMember(Long memberId){
        return verifyMember(memberId);
    }

    public Page<Member> getMembers(int page, int size){
        return memberRepository.findAll(PageRequest.of(page,size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(Long memberId){
        Member deleteMember = verifyMember(memberId);
        memberRepository.delete(deleteMember);
    }

    public void checkDuplicateUsername(String username){
        memberExistCheck(username);
    }

    private void memberExistCheck(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXIST);
        }
    }

    private Member verifyMember(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
