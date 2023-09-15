package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
