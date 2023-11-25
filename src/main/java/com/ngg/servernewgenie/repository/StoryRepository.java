package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByUserNum(Long userNum);

    Story findByStoryId(Long storyId);

    List<Story> findByUserNumIn(List<Long> userNums);

    @Query("SELECT s FROM Story s")
    List<Story> findAllStories();
}
