package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;



@Service
public class StoryService {

    private static final Logger logger = Logger.getLogger("com.ngg.servernewgenie.service.StoryService");

    @Autowired
    private StoryRepository storyRepository;

    //--------------sh

    @Transactional
    public Story saveStory(Story story) {
        return storyRepository.save(story);
    }

    @Transactional(readOnly = true)
    public List<Story> getUserStories(Long userNum) {
        return storyRepository.findByUserNum(userNum);
    }

    @Transactional(readOnly = true)
    public Story getStory(Long storyId) {
        return storyRepository.findByStoryId(storyId);
    }

    @Transactional(readOnly = true)
    public List<Story> getFollowingStories(Long fromUserNum) {

        // todo : Logic to get stories from following users

        return null;
    }

    @Transactional(readOnly = true)
    public List<Story> getAllStories() {
        return storyRepository.findAllStories();
    }

    @Transactional
    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }
    //-----------------


    public void saveStoryExplain(Long storyId, String storyExplain) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            story.setStoryExplain(storyExplain);
            storyRepository.save(story);
        } else {
            // 해당 storyId에 대한 스토리를 찾을 수 없는 경우에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found for storyId: " + storyId);
        }
    }

    public String viewStoryExplain(Long storyId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            return story.getStoryExplain();
        } else {
            // 해당 storyId에 대한 스토리를 찾을 수 없는 경우에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found for storyId: " + storyId);
        }
    }


}
