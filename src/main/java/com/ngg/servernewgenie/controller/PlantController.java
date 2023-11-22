package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.Plant;
import com.ngg.servernewgenie.repository.PlantRepository;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/Plant")
public class PlantController {

    private PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

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

    /*
    // 로그인 세션 있다고 가정했을 때 (아직 로그인 관련 구현 X)
    private final PlantService plantService;
    private final UserService userService; // UserService에 사용자 정보 관련 메서드가 있다고 가정

    @Autowired
    public PlantController(PlantService plantService, UserService userService) {
        this.plantService = plantService;
        this.userService = userService;
    }

    // 로그인 세션 있다고 가정했을 때 (아직 로그인 관련 구현 X)
    @GetMapping("/plantName/save")
    public List<Plant> getPlantsForCurrentUser(HttpSession session) {
        // 세션을 통해 현재 로그인된 사용자 정보 가져오기
        User currentUser = (User) session.getAttribute("currentUser"); // 세션에서 사용자 정보 가져오는 예시

        if (currentUser != null) {
            // 현재 로그인된 사용자의 식물 정보 조회
            return plantService.findPlantsByUserId(currentUser.getUserId());
        } else {
            // 세션이 없거나 로그인되지 않은 경우 처리
            return Collections.emptyList();
        }
    }
    */


    //Post로 plant 추가
//    @PostMapping
//    public Plant put(@RequestParam int plt_id, @RequestParam int user_num, @RequestParam String plt_name, @RequestParam String plt_img){
//        return plantRep.save(new Plant(plt_id, user_num, plt_name, plt_img));
//    }



}