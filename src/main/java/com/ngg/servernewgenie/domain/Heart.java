package com.ngg.servernewgenie.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "heart")
public class Heart implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, name = "h_id")
    private Long h_id;

    // 성능 최적화를 위해서는
    // @ManyToOne(fetch = FetchType.LAZY) 라고 옵션 붙이기
    // FetchType.LAZY : 연관된 엔티티를 처음으로 접근할 때까지 데이터를 가져오지 않음
    @ManyToOne
    @JoinColumn(name = "liker", referencedColumnName = "user_num", nullable = false)
    private User liker;

    @ManyToOne
    @JoinColumn(name = "story_id", referencedColumnName = "story_id", nullable = false)
    private Story story_id;

    public Heart(Long h_id, User liker, Story story_id) {
        this.h_id = h_id;
        this.liker = liker;
        this.story_id = story_id;
    }
}