package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Authority;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.model.SignRequest;
import com.ngg.servernewgenie.model.SignResponse;
import com.ngg.servernewgenie.provider.JwtProvider;
import com.ngg.servernewgenie.repository.LoginRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class SignService {
    @Autowired
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final static Logger LOG = Logger.getGlobal();

    public SignResponse login(SignRequest request) throws Exception {
        User user = loginRepository.findByUserId(request.getUser_id()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getUser_pwd(), user.getUserPwd())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(user.getUserId())
                .name(user.getUserName())
                .email(user.getUserEmail())
                .roles(user.getRoles())
                .token(jwtProvider.createToken(user.getUserId(), user.getRoles()))
                .build();
}
    public boolean register(SignRequest request) throws Exception {
        try {
            User user = User.builder()
                    .userId(request.getUser_id())
                    .userPwd(passwordEncoder.encode(request.getUser_pwd()))
                    .userName(request.getUser_name())
                    .userEmail(request.getUser_email())
                    .build();

            user.setRoles(Collections.singletonList(Authority.builder().user_name("ROLE_USER").build()));

            LOG.severe(String.valueOf(user.getUserId() + user.getUserEmail() + user.getRoles()));

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다."+ User.builder().userId(request.getUser_id()).userName(request.getUser_name()));
        }
        return true;
    }

    public SignResponse getUser(String username) throws Exception {
        User user = loginRepository.findByUserId(username)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(user);
    }

}
