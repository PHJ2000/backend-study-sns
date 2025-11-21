package com.example.devSns.web.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
        @NotBlank String username,
        String nickname,
        String bio
) {}
