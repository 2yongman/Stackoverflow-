package BackEnd.preProject.member.dto;

import BackEnd.preProject.exception.BusinessLogicException;
import BackEnd.preProject.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignupDto {

    @NotBlank(message = "ID를 입력해주세요.")
    @Pattern(regexp = "^.{6,}$", message = "ID는 6글자 이상이어야 합니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
            message = "패스워드는 8글자 이상이어야 하며, 최소한 숫자1개, 특수문자 1개를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "패스워드 확인을 입력해주세요.")
    private String checkPassword;

    public void checkPasswordMatch(){
        if (!password.equals(checkPassword)){
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }
}
