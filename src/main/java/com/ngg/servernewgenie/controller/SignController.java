package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.model.SignRequest;
import com.ngg.servernewgenie.model.SignResponse;
import com.ngg.servernewgenie.repository.UserRepository;
import com.ngg.servernewgenie.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final UserRepository userRepository;
    private final SignService userService;


    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> signin(@RequestBody SignRequest request) throws Exception{
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(userService.register(request), HttpStatus.OK);
    }

    @GetMapping("/User/get")
    public ResponseEntity<SignResponse> getUser(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

}
