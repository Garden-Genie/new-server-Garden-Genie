package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Heart;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.dto.HeartRequestDTO;
import com.ngg.servernewgenie.dto.HeartResponseDTO;
import com.ngg.servernewgenie.repository.HeartRepository;
import com.ngg.servernewgenie.repository.StoryRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HeartService {

    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final HeartRepository heartRepository;

    @Autowired
    public HeartService(UserRepository userRepository, StoryRepository storyRepository, HeartRepository heartRepository) {
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
        this.heartRepository = heartRepository;
    }

    public HeartResponseDTO addLike(HeartRequestDTO requestDTO) {
        User liker = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        Story story = storyRepository.findById(requestDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("스토리가 존재하지 않습니다."));

//        User liker = new User(requestDTO.getUserId());
//        Story story = new Story();
//        story.setStory_id(requestDTO.getStoryId());


        Heart existingLike = heartRepository.findByLikerAndStory(liker, story);

        if (existingLike == null) {
            Heart newLike = new Heart(null, liker, story);
            Heart savedLike = heartRepository.save(newLike);

            return new HeartResponseDTO(savedLike.getH_id(), requestDTO.getUserId(), requestDTO.getStoryId());
        } else {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }
    }

    public void deleteLike(HeartRequestDTO requestDTO) {
        User liker = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        Story story = storyRepository.findById(requestDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("스토리가 존재하지 않습니다."));

//        User liker = new User(requestDTO.getUserId());
//        Story story = new Story();
//        story.setStory_id(requestDTO.getStoryId());

        Heart existingLike = heartRepository.findByLikerAndStory(liker, story);

        if (existingLike != null) {
            heartRepository.delete(existingLike);
        } else {
            throw new RuntimeException("좋아요를 누르지 않았습니다.");
        }
    }

    public List<HeartResponseDTO> getLikedStoriesByUser(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        List<Heart> hearts = heartRepository.findByLiker(user);

        return hearts.stream()
                .map(heart -> new HeartResponseDTO(heart.getH_id(), userNum, heart.getStory().getStoryId()))
                .collect(Collectors.toList());
    }

    public int getLikeCountByStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("스토리가 존재하지 않습니다."));

        List<Heart> hearts = heartRepository.findByStory(story);

        return hearts.size();
    }


}