package com.ngg.servernewgenie.dto;

import java.io.Serializable;

public class StoryExplainResponseDto implements Serializable{
    private String storyExplain;

    public StoryExplainResponseDto(String storyExplain) {
        this.storyExplain = storyExplain;
    }

    // Getters and setters (생성자만으로도 충분할 수 있지만, 필요에 따라 Getter/Setter 추가 가능)
    public String getStoryExplain() {
        return storyExplain;
    }

    public void setStoryExplain(String storyExplain) {
        this.storyExplain = storyExplain;
    }
}
