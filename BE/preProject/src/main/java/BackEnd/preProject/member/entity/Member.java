package BackEnd.preProject.member.entity;

import BackEnd.preProject.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.matcher.StringSetMatcher;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberId;

    @Setter
    @Column(nullable = false,unique = true, length = 20)
    private String username;

    @Setter
    @Column(nullable = false, length = 40)
    private String email;

    @Setter
    @Column(nullable = false, length = 40)
    private String nickname;

    @Setter
    @Column(nullable = false, length = 100)
    private String password;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public Member(String username, String email, String nickname, String password){
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Member(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}
