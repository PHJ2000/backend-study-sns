package com.example.devSns.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
        @NotNull Long memberId,
        @NotBlank String title,
        String content
) {}
