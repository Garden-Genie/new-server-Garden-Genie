package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
