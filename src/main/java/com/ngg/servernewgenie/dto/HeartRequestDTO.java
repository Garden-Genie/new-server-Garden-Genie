package com.ngg.servernewgenie.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDTO {
    private Long userId;
    private Long storyId;

    public HeartRequestDTO(Long userId, Long storyId) {
        this.userId = userId;
        this.storyId = storyId;
    }
}