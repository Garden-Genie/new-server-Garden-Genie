package com.ngg.servernewgenie.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserByUserNum(Long userNum) {
        // UserRepository를 사용하여 사용자 정보를 조회
        return userRepository.findByUserNum(userNum);
    }
}
