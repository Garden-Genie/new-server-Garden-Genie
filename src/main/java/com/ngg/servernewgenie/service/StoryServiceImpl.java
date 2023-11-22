package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public void save(Story story) {
        storyRepository.save(story);
    }

    @Override
    public List<Story> findByUserId(Long userId) {
        return storyRepository.findByUserId(userId);
    }

    @Override
    public Story findById(Long storyId) {
        return storyRepository.findById(storyId).orElse(null);
    }

    @Override
    public List<Story> findByUserIdAndDateGreaterThan(Long userId, Date date) {
        return storyRepository.findByUserIdAndStoryDateGreaterThan(userId, date);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }

    @Override
    public void delete(Long storyId) {
        storyRepository.deleteById(storyId);
    }
}
