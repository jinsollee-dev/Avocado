package com.example.avocado.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty
    @NotBlank(message="이메일(ooo@ooo.ooo) 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 15, message = "닉네임은 2 ~ 15자 사이로 입력해주세요")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty
    private String name;
    private String phone;

    //파일==================================================
    private MultipartFile files;
}
