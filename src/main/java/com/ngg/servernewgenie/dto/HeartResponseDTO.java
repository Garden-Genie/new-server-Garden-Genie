package com.ngg.servernewgenie.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartResponseDTO {
    private Long h_id;
    private Long likerId;
    private Long storyId;

    public HeartResponseDTO(Long h_id, Long likerId, Long storyId) {
        this.h_id = h_id;
        this.likerId = likerId;
        this.storyId = storyId;
    }
}
