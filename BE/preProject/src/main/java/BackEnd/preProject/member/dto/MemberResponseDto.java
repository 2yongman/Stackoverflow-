package BackEnd.preProject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private String username;

    private String email;

    private String nickname;

    private LocalDateTime createdAt;
}
