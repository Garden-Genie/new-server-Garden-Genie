package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/User")
public class UserController {
    private UserRepository userRep;
    @Autowired
    public UserController(UserRepository userRep){
        this.userRep = userRep;
    }

    //Post로 유저 추가
    @PostMapping
    public User put(@RequestParam String user_name, @RequestParam String user_id, @RequestParam String user_email, @RequestParam String user_pwd){
        return userRep.save(new User(user_name, user_id, user_email, user_pwd));
    }

    //테이블 리스트 가져오기
    @GetMapping
    public Iterable<User> list(){
        return userRep.findAll();
    }
//
//    //id로 테이블 값 가져오기
//    @GetMapping(value = "/{user_id}")
//    public Optional<user> findOne(@PathVariable String user_id){
//        return userRep.findById(user_id);
//    }

    //테이블 값 삭제
//    @DeleteMapping
//    public void delete(@RequestParam String user_id){
//        userRep.deleteById(user_id);
//    }


}

