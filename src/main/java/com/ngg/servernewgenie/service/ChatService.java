package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.config.ChatGptConfig;
import com.ngg.servernewgenie.dto.ChatGptRequestDto;
import com.ngg.servernewgenie.dto.ChatGptResponseDto;
import com.ngg.servernewgenie.dto.QuestionRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.logging.Logger;

import static com.ngg.servernewgenie.config.ChatGptConfig.*;

@Service
public class ChatService {

    private static final Logger logger = Logger.getLogger("com.example.gardengenie.service.ChatService");

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        ChatGptRequestDto chatGptRequestDto = new ChatGptRequestDto();
        chatGptRequestDto.setModel("gpt-3.5-turbo");

        chatGptRequestDto.setMessages(Collections.singletonList(
                ChatGptRequestDto.Message.builder()
                        .role("user")
                        .content(requestDto.getBody())
                        .build()
        ));

        return this.getResponse(this.buildHttpEntity(chatGptRequestDto));
    }
}
