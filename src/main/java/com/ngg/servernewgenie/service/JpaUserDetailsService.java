package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = loginRepository.findByUserId(username).orElseThrow(
                ()->new UsernameNotFoundException("Invalid authentication!")
        );

        return new CustomUserDetails(user);
    }
}
