package com.ngg.servernewgenie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Choice implements Serializable {

    private Message message;
    private Integer index;
    @JsonProperty("finish_reason")
    private String finishReason;

    public String getContent() {
        if (message != null && message.getContent() != null) {
            return message.getContent();
        }
        return null;
    }

    @Getter
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    @Builder
    public Choice(Message message, Integer index, String finishReason) {
        this.message = message;
        this.index = index;
        this.finishReason = finishReason;
    }
}