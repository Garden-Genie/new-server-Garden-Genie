package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.stream.Collectors;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;



@Service
public class StoryService {

    private static final Logger logger = Logger.getLogger("com.ngg.servernewgenie.service.StoryService");

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    public StoryService(FollowRepository followRepository, StoryRepository storyRepository) {
        this.followRepository = followRepository;
        this.storyRepository = storyRepository;
    }

    @Transactional
    public void updateUploadByStoryId(Long storyId) {
        storyRepository.updateUploadStatus(storyId);
    }

    @Transactional(readOnly = true)
    public List<Story> getUserStories(Long userNum) {
        return storyRepository.findByUser_UserNum(userNum);
    }

    @Transactional(readOnly = true)
    public Story getStory(Long storyId) {
        //return storyRepository.findById(storyId);
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        return optionalStory.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Story> getFollowingStories(Long fromUserNum) {
        List<Follow> follows = followRepository.findByFromUser_UserNum(fromUserNum);
        List<Long> toUserNums = follows.stream()
                .map(follow -> follow.getToUser().getUserNum())
                .collect(Collectors.toList());

        if (toUserNums.isEmpty()) {
            return Collections.emptyList();
        }

        return storyRepository.findByUser_UserNumIn(toUserNums);
    }

    @Transactional(readOnly = true)
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @Transactional
    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }


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

    public String viewStoryCondition(Long storyId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            return story.getStoryCondition();
        } else {
            // 해당 storyId에 대한 스토리를 찾을 수 없는 경우에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found for storyId: " + storyId);
        }
    }


    public String viewStoryMusic(Long storyId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            return story.getStoryMusic();
        } else {
            // 해당 storyId에 대한 스토리를 찾을 수 없는 경우에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found for storyId: " + storyId);
        }
    }

    public String viewStoryPoem(Long storyId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            return story.getStoryPoem();
        } else {
            // 해당 storyId에 대한 스토리를 찾을 수 없는 경우에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found for storyId: " + storyId);
        }
    }
}
