package com.ngg.servernewgenie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "story")
public class Story implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, name = "story_id")
    private int story_id;

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

    public Story(int story_id, LocalDateTime story_date, String story_explain, String story_music, String story_poem, String story_condition, Plant plant, boolean upload){
        this.story_id = story_id;
        this.story_date = story_date;
        this.story_explain = story_explain;
        this.story_music = story_music;
        this.story_poem = story_poem;
        this.story_condition = story_condition;
        this.plant = plant;
        this.upload = upload;
    }
}
