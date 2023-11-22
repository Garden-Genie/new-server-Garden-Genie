package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Heart;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.dto.HeartDTO;
import com.ngg.servernewgenie.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HeartService {

    @Autowired
    private HeartRepository heartRepository;

    public HeartDTO addLike(Long userId, Long storyId) {
        User liker = new User(userId);
        Story story = new Story(storyId);

        Heart existingLike = heartRepository.findByLikerAndStory_id(liker, story);

        if (existingLike == null) {
            Heart newLike = new Heart(null, liker, story);
            Heart savedLike = heartRepository.save(newLike);

            return new HeartDTO(savedLike.getH_id(), userId, storyId);
        } else {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }
    }

    public void deleteLike(Long userId, Long storyId) {
        User liker = new User(userId);
        Story story = new Story(storyId);

        Heart existingLike = heartRepository.findByLikerAndStory_id(liker, story);

        if (existingLike != null) {
            heartRepository.delete(existingLike);
        } else {
            throw new RuntimeException("좋아요를 누르지 않았습니다.");
        }
    }

    public int getLikeCountByStory(Long storyId) {
        Story story = new Story(storyId);
        List<Heart> hearts = heartRepository.findByStory_id(story);

        return hearts.size();
    }

    public List<HeartDTO> getLikedStoriesByUser(Long userId) {
        User user = new User(userId);
        List<Heart> hearts = heartRepository.findByLiker(user);

        return hearts.stream()
                .map(heart -> new HeartDTO(heart.getH_id(), userId, heart.getStory_id().getStory_id()))
                .collect(Collectors.toList());
    }
}
