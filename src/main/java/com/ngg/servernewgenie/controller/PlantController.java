package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.domain.Plant;
import com.ngg.servernewgenie.repository.PlantRepository;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/Plant")
public class PlantController {


    private PlantService plantService;
    private PlantRepository plantRepository;
    private final Map<String, String> replacementMap; // key-value 쌍을 저장하는 Map


    @Autowired
    public PlantController(PlantService plantService, PlantRepository plantRepository) {
        this.plantService = plantService;
        this.plantRepository = plantRepository;

        // 특정한 key-value 쌍을 Map에 추가하는 예시
        this.replacementMap = new HashMap<>();
        replacementMap.put("<Clusia>", "클루시아");
        replacementMap.put("<Fan-Palms>", "관음죽");
        replacementMap.put("<Lvicks plant>", "장미허브");
        replacementMap.put("<Pachira>", "파키라");
        replacementMap.put("<Wind orchid>", "풍란");
        replacementMap.put("<caladium>", "칼라디움");
        replacementMap.put("<carnation>", "카네이션");
        replacementMap.put("<creeping fig>", "푸밀라 고무나무");
        replacementMap.put("<croton>", "크로톤");
        replacementMap.put("<eucalyptus>", "유칼립투스");
        replacementMap.put("<freesia>", "프리지아");
        replacementMap.put("<geranium>", "제라늄");
        replacementMap.put("<poinsettia>", "포인세티아");
        replacementMap.put("<ribbon plant>", "접란");

    }

    // Get /name
    // 로그인한 사용자의 가장 최근 생성된 plantname 가져오기
    @GetMapping("/name")
    public ResponseEntity<String> getPlantNameOfLoggedInUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            String pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            if (pltName != null) {
                return ResponseEntity.ok(pltName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user details");
        }
    }

    // Get /name/kr
    // 로그인한 사용자의 가장 최근 생성된 plantname을 한국어로 가져오기
    @GetMapping("/name/kr")
    public ResponseEntity<String> getKRPlantNameOfLoggedInUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            String pltName = plantRepository.findMostRecentPltNameByUserId(userId);

            // Map에서 특정 key에 해당하는 값이 있는지 확인하고, 있다면 대체값으로 교체
            if (pltName != null && replacementMap.containsKey(pltName)) {
                pltName = replacementMap.get(pltName);
                return ResponseEntity.ok(pltName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found or replacement not specified");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user details");
        }
    }

    /*
    @PostMapping("/plantName/save")
    public ResponseEntity<String> savePlantName(@RequestParam("userId") Long userId,
                                                @RequestParam("plantName") String plantName) {
        try {
            plantService.savePlantNameByUserId(userId, plantName);
            return ResponseEntity.ok("Plant name saved successfully for user with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save plant name: " + e.getMessage());
        }
    }
    */

}