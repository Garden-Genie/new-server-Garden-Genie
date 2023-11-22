package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Heart;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByLikerAndStory_id(User liker, Story story_id);

    List<Heart> findByLiker(User liker);

    List<Heart> findByStory_id(Story story);
}
