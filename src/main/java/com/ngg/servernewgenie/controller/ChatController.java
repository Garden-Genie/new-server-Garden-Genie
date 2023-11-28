package com.ngg.servernewgenie.controller;


import com.ngg.servernewgenie.config.BaseException;
import com.ngg.servernewgenie.config.BaseResponseStatus;
import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.dto.ChatGptResponseDto;
import com.ngg.servernewgenie.dto.QuestionRequestDto;
import com.ngg.servernewgenie.repository.PlantRepository;
import com.ngg.servernewgenie.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat-gpt")
public class ChatController {
    private final ChatService chatService;
    private final PlantRepository plantRepository;

    public ChatController(ChatService chatService, PlantRepository plantRepository) {
        this.chatService = chatService;
        this.plantRepository = plantRepository;
    }

    @PostMapping("/question/name")
    public ChatGptResponseDto sendName() {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String pltName = null;


        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 로그 출력
            System.out.println("==================userId: " + userId);
        } else if (principal instanceof String) {
            String userId = (String) principal;

            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 로그 출력
            System.out.println("==================userId: " + userId);
            // 사용자 ID를 기반으로 식물 이름 조회하는 처리 코드
        } else { // CustomUserDetails도 아니고 String도 아닌 경우에 대한 처리 코드
            // 사용자 정보를 가져올 수 없는 경우 또는 예외 처리
            // 로그 출력
            System.out.println("====================Unexpected principal type:"+ principal.getClass().getName());
            throw new BaseException(BaseResponseStatus.PRINCIPAL_TYPE_ERROR, HttpStatus.BAD_REQUEST);
        }
        // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
        if ("No object detected".equals(pltName)) {
            throw new BaseException(BaseResponseStatus.NO_OBJECT_DETECTES_ERROR, HttpStatus.BAD_REQUEST);
        }


        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("다음 식물 이름을 한국어로 말해줘. <" + pltName + "> 이때 응답은 오직 식물 이름만 포함해줘. 즉 단어로 말해줘."+
                "예를 들면 request: <Clusia>, response: '클루시아' 처럼 응답해줘."+
                "request: <Fan-Palms>, response: '관음죽'"+
                "request: <Lvicks plant>, response: '장미허브'"+
                "request: <Pachira>, response: '파키라'" +
                "request: <Wind orchid>, response: '풍란'"+
                "request: <caladium>, response: '칼라디움'"+
                "request: <carnation>, response: '카네이션'" +
                "request: <creeping fig>, response: '푸밀라 고무나무'"+
                "request: <croton>, response: '크로톤'"+
                "request: <eucalyptus>, response: '유칼립투스'" +
                "request: <freesia>, response: '프리지아'"+
                "request: <geranium>, response: '제라늄'"+
                "request: <poinsettia>, response: '포인세티아'"+
                "request: <ribbon plant>, response: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        return chatService.askQuestion(requestDto);
    }

    @PostMapping("/question/explain")
    public ChatGptResponseDto sendExplain() {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String pltName = null;

        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
        } else if (principal instanceof String) {
            String userId = (String) principal;
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 사용자 ID를 기반으로 식물 이름 조회하는 처리 코드
        } else { // CustomUserDetails도 아니고 String도 아닌 경우에 대한 처리 코드
            // 사용자 정보를 가져올 수 없는 경우 또는 예외 처리
            // 로그 출력
            System.out.println("====================Unexpected principal type:"+ principal.getClass().getName());
            throw new BaseException(BaseResponseStatus.PRINCIPAL_TYPE_ERROR, HttpStatus.BAD_REQUEST);
        }

        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("식물 <" + pltName + ">에 대해서 2줄 반 이내로 설명해줘." +
                "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        return chatService.askQuestion(requestDto);
    }

    private List<Map<String, String>> getMessages(QuestionRequestDto requestDto) {
        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", requestDto.getBody());
        messages.add(userMessage);

        return messages;
    }

    @PostMapping("/question/music")
    public ChatGptResponseDto sendMusic() {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String pltName = null;

        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
        } else if (principal instanceof String) {
            String userId = (String) principal;
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 사용자 ID를 기반으로 식물 이름 조회하는 처리 코드
        } else { // CustomUserDetails도 아니고 String도 아닌 경우에 대한 처리 코드
            // 사용자 정보를 가져올 수 없는 경우 또는 예외 처리
            // 로그 출력
            System.out.println("====================Unexpected principal type:"+ principal.getClass().getName());
            throw new BaseException(BaseResponseStatus.PRINCIPAL_TYPE_ERROR, HttpStatus.BAD_REQUEST);
        }

        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("Your answer form should be like this: <title>-'artist'. Don't say any other words except for this."+
                "Give me a song related to <" + pltName + ">. The song should have more than 1,000 hits on Youtube. " +
                "예를 들면 response: <Lemon tree>-'fools garden' 처럼 응답해줘.");


//        requestDto.setBody("Your answer form should be like this: <title>-'artist'. Don't say any other words except for this."
//                +"Give me a song related to <" + pltName + ">. The song should have more than 1,000 hits on Youtube. " +
//                "If you don't find a song that meets the criteria, please suggest a song about plants that meets the above criteria.");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        return chatService.askQuestion(requestDto);
    }

    @PostMapping("/question/poem")
    public ChatGptResponseDto sendPoem() {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String pltName = null;

        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
        } else if (principal instanceof String) {
            String userId = (String) principal;
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 사용자 ID를 기반으로 식물 이름 조회하는 처리 코드
        } else { // CustomUserDetails도 아니고 String도 아닌 경우에 대한 처리 코드
            // 사용자 정보를 가져올 수 없는 경우 또는 예외 처리
            // 로그 출력
            System.out.println("====================Unexpected principal type:"+ principal.getClass().getName());
            throw new BaseException(BaseResponseStatus.PRINCIPAL_TYPE_ERROR, HttpStatus.BAD_REQUEST);
        }

        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("식물 <" + pltName + ">과 관련된 시를 짧게 창작해줘."+
                "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        return chatService.askQuestion(requestDto);
    }

    @PostMapping("/question/advice")
    public ChatGptResponseDto sendAdvice() {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String pltName = null;

        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
        } else if (principal instanceof String) {
            String userId = (String) principal;
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            // 사용자 ID를 기반으로 식물 이름 조회하는 처리 코드
        } else { // CustomUserDetails도 아니고 String도 아닌 경우에 대한 처리 코드
            // 사용자 정보를 가져올 수 없는 경우 또는 예외 처리
            // 로그 출력
            System.out.println("====================Unexpected principal type:"+ principal.getClass().getName());
            throw new BaseException(BaseResponseStatus.PRINCIPAL_TYPE_ERROR, HttpStatus.BAD_REQUEST);
        }

        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("Your answer form should start with this: '몇가지 조언을 드리겠습니다.'"+
                "식물 <" + pltName + ">를 키울 때 주의 할 점이나 조언을 2줄 반 이내로 해줘."+
                "번호 없이 구어체로 말해줘."+ "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        return chatService.askQuestion(requestDto);
    }

}