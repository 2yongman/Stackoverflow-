package BackEnd.preProject.member.service;

import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.repository.MemberRepository;
import BackEnd.preProject.security.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository,
                         ApplicationEventPublisher publisher,
                         PasswordEncoder passwordEncoder,
                         CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member signup(Member member){
        memberExistCheck(member.getUsername());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        return memberRepository.save(member);
    }

    public Member update(Long memberId,Member member, String username){

        Member findMember = verifyMember(memberId);
        if (String.valueOf(findMember.getUsername()).equals(username)){
            Optional.ofNullable(member.getEmail())
                    .ifPresent(email -> findMember.setEmail(email));
            Optional.ofNullable(member.getNickname())
                    .ifPresent(nickname -> findMember.setNickname(nickname));
            Optional.ofNullable(member.getPassword())
                    .ifPresent(password -> findMember.setPassword(password));

            return memberRepository.save(findMember);
        }

        else throw new IllegalArgumentException("본인이 아닙니다.");

    }

    public Member getMember(Long memberId,String username){
        Member findMember = verifyMember(memberId);
        if (String.valueOf(findMember.getUsername()).equals(username)){
            return verifyMember(memberId);
        }
        else throw new IllegalArgumentException("본인이 아닙니다.");
    }

    public Page<Member> getMembers(int page, int size){
        return memberRepository.findAll(PageRequest.of(page,size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(Long memberId, String username){
        Member usernameFromDb = findMemberByUsername(username);
        Member deleteMember = verifyMember(memberId);

        long memberIdFromDb = usernameFromDb.getMemberId();
        long memberIdFromVerify = deleteMember.getMemberId();

        if (memberIdFromDb != memberIdFromVerify) throw new IllegalArgumentException("본인이 아닙니다.");

        memberRepository.delete(deleteMember);
    }

    public void checkDuplicateUsername(String username){
        memberExistCheck(username);
    }

    public void memberExistCheck(String username){
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

    public Member findMemberByUsername(String username){
        Member findMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );
        return findMember;
    }

    public void checkQuestionUsernameEqualsUsername(String questionUsername, String username){
        Member questionMember = this.findMemberByUsername(questionUsername);
        Member user = this.findMemberByUsername(username);
        if (questionMember.getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("질문을 작성한 사용자만이 채택할 수 있습니다.");
        }
    }
}
