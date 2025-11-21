package com.example.devSns.web.dto;

import jakarta.validation.constraints.NotNull;

public record LikeRequest(
        @NotNull Long memberId
) {}
