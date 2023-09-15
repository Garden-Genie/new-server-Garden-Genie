package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Integer> {
}
