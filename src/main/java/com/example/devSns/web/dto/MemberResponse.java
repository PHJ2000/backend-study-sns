package com.example.devSns.web.dto;

import com.example.devSns.domain.Member;

public record MemberResponse(
        Long id,
        String username,
        String nickname,
        String bio
) {
    public static MemberResponse from(Member m) {
        return new MemberResponse(
                m.getId(),
                m.getUsername(),
                m.getNickname(),
                m.getBio()
        );
    }
}
