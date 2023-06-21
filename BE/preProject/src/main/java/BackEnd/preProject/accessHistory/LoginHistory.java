package BackEnd.preProject.accessHistory;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column
    private LocalDateTime accessDatetime;

    public LoginHistory(String username,
                        LocalDateTime accessDatetime){
        this.username = username;
        this.accessDatetime = accessDatetime;
    }

}
