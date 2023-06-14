package BackEnd.preProject.member.entity;

import BackEnd.preProject.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.matcher.StringSetMatcher;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberId;

    @Column(nullable = false,unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    public Member(String username, String email, String nickname, String password){
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
