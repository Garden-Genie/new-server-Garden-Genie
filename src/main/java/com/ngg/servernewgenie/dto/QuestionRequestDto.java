package com.ngg.servernewgenie.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDto implements Serializable {
    private String question;
    private String additionalDescription;

    public void setBody(String question) {
        this.question = question;
    }

    public String getBody() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }
}
