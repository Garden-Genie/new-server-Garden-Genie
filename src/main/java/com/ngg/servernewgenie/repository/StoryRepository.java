package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Modifying
    @Query("UPDATE Story s SET s.upload = 1 WHERE s.storyId = :storyId")
    void updateUploadStatus(@Param("storyId") Long storyId);

    List<Story> findByUser_UserNum(Long userNum);

    List<Story> findByUser_UserNumIn(List<Long> userNums);

}
