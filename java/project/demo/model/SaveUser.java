package project.demo.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class SaveUser {

    @Pattern(regexp = "^[A-z0-9]{6,12}$", message = "영문과 숫자로 이루어진 6~12자여야 합니다.")
    private String username;

    @Pattern(regexp = "^[A-z0-9!@#$]{6,20}$", message = "영문, 숫자, 특수문자(!, @, #, $)로 이루어진 6~20자여야 합니다.")
    private String password;

    @Pattern(regexp = "^[A-z가-힇0-9]{2,10}$", message = "닉네임은 한글, 영문, 숫자로 구성할 수 있습니다.(2~10자 제한)")
    private String nickname;
}
