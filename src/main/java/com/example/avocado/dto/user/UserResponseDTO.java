package com.example.avocado.dto.user;


import com.example.avocado.domain.User;
import com.example.avocado.domain.UserImage;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDTO {

    private String username;

    private String nickname;

    private String password;

    private String name;

    private String phone;

    private String role;

    private String url;

    @Builder
    public UserResponseDTO(User user, UserImage userImage) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.url = userImage.getUrl();
    }

}
