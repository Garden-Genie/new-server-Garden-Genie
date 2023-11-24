package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface LoginRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String user_id);
}