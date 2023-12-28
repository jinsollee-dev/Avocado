package com.example.avocado.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(length = 50, nullable = false)
    @Email
    private String id;
    @Column(length = 50, nullable = false)
    private String userName;
    @Column(length = 50, nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String phone;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false, length = 1 )
    @ColumnDefault("1") //회원탈퇴여부(0:탈퇴,1:가입)
    private String deleteCheck;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name ="modDate" )
    private LocalDateTime modDate;
}
