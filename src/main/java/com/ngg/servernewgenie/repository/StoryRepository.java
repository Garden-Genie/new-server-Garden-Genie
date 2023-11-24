package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

//    List<Story> findByUserId(Long userId);

//    List<Story> findByUserIdAndStoryDateGreaterThan(Long userId, Date date);

}
