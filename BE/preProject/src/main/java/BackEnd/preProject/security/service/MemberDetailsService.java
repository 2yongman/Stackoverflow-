package BackEnd.preProject.security.service;

import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.repository.MemberRepository;
import BackEnd.preProject.security.utils.CustomAuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;


//    private final CustomA

    //Todo member디테일서비스 생성자
    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }


    //Todo 유저 이름으로 멤버레포에서 찾아온 다음, 멤버디테일에 주입한 결과를 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return new MemberDetails(findMember);
    }


    //Todo 멤버디테일. 생성자로 멤버를 받아서 멤버디테일 객체 생성 - 클래스
    private final class MemberDetails extends Member implements UserDetails {

        MemberDetails(Member member) {
            setUsername(member.getUsername());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setNickname(member.getNickname());

        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


}
