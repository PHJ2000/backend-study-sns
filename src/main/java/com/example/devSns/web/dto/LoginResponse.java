package com.example.devSns.web.dto;

import com.example.devSns.domain.Member;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long memberId,
        String username
) {
    public static LoginResponse of(String token, Member member) {
        return new LoginResponse(token, "Bearer", member.getId(), member.getUsername());
    }
}
