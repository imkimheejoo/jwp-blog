package techcourse.myblog.domain;

import lombok.Getter;

@Getter
public class UserDto {
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;

    public UserDto(String userName, String email, String password, String confirmPassword) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}