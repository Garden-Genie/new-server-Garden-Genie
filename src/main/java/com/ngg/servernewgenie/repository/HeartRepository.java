package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
