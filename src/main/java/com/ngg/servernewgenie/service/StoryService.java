package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StoryService {

    private static final Logger logger = Logger.getLogger("com.ngg.servernewgenie.service.StoryService");

    @Autowired
    private StoryRepository storyRepository;


    public void saveStoryExplain(int storyId, String storyExplain) {
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

    public String viewStoryExplain(int storyId) {
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