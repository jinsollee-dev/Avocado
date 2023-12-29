package com.example.avocado.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private String phone;
    private String role;

    //    @Column(columnDefinition = "boolean default true") //회원탈퇴여부(0:탈퇴,1:가입)
    private Boolean deleteCheck = true;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    //파일==================================================
    private MultipartFile files;
}
