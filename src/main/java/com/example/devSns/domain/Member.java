package com.example.devSns.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String username;    // 로그인/아이디 용

    @Column(length = 50)
    private String nickname;    // 화면용 이름

    @Column(length = 255)
    private String bio;         // 자기소개 (옵션)

    @Builder
    private Member(String username, String nickname, String bio) {
        this.username = username;
        this.nickname = nickname;
        this.bio = bio;
    }

    public void updateProfile(String nickname, String bio) {
        this.nickname = nickname;
        this.bio = bio;
    }
}
