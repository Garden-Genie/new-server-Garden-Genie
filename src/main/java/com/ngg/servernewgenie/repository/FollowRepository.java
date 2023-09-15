package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
