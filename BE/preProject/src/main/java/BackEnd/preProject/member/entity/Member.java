package BackEnd.preProject.member.entity;

import BackEnd.preProject.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.matcher.StringSetMatcher;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE MEMBER SET deleted_at = CURRENT_TIMESTAMP where member_id = ?")
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

    private LocalDateTime deletedAt;

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
