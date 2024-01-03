package com.example.avocado.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder		// DTO -> Entity화
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    @Email
    private String username;
    @Column(length = 50, nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String phone;
    @Column(nullable = false)
    private String role;



//    @Column(columnDefinition = "boolean default true") //회원탈퇴여부(0:탈퇴,1:가입)
    @Column
    private Boolean deleteCheck = true;

    //파일==================================================
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private UserImage userImage;

}
