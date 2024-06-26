package com.ngg.servernewgenie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "story")
public class Story implements Serializable {

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "story_id")

    private Long storyId;

    @ManyToOne
    @JoinColumn(name = "user_num", referencedColumnName = "user_num", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "plt_id", referencedColumnName = "plt_id", nullable = false)
    private Plant plant;

    @Column(nullable = false, name = "story_date")
    private LocalDateTime story_date;

    @Column(nullable = true, name = "story_explain", columnDefinition = "TEXT")
    private String story_explain;

    @Column(nullable = true, name = "story_music")
    private String story_music;

    @Column(nullable = true, name = "story_poem", columnDefinition = "TEXT")
    private String story_poem;

    @Column(nullable = true, name = "story_condition", columnDefinition = "TEXT")
    private String story_condition;

    @Column(nullable = false, name = "upload", columnDefinition = "boolean default false")
    private boolean upload;

    public Story(Long storyId, LocalDateTime story_date, String story_explain, String story_music, String story_poem, String story_condition, Plant plant, boolean upload, User user){
        this.storyId = storyId;
        this.story_date = story_date;
        this.story_explain = story_explain;
        this.story_music = story_music;
        this.story_poem = story_poem;
        this.story_condition = story_condition;
        this.plant = plant;
        this.upload = upload;
        this.user = user;
    }

    public void setStoryExplain(String storyExplain) {
        this.story_explain = storyExplain;
    }

    public String getStoryExplain() {
        return story_explain;
    }

    public void setStoryCondition(String storyCondition) {
        this.story_condition = storyCondition;
    }

    public String getStoryCondition() {
        return story_condition;
    }

    public void setStoryMusic(String storyMusic) {
        this.story_music = storyMusic;
    }

    public String getStoryMusic() {
        return story_music;
    }
    public void setStoryPoem(String storyPoem) {
        this.story_poem = storyPoem;
    }

    public String getStoryPoem() {
        return story_poem;
    }
}
