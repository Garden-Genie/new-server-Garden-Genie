package com.ngg.servernewgenie.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartDTO {
    private Long h_id;
    private Long liker_id;
    private Long story_id;

    public HeartDTO(Long h_id, Long liker_id, Long story_id) {
        this.h_id = h_id;
        this.liker_id = liker_id;
        this.story_id = story_id;
    }

}