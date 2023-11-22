package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Story;

import java.util.Date;
import java.util.List;

public interface StoryService {

    void save(Story story);

    List<Story> findByUserId(Long userId);

    Story findById(Long storyId);

    List<Story> findByUserIdAndDateGreaterThan(Long userId, Date date);

    List<Story> findAll();

    void delete(Long storyId);
}
