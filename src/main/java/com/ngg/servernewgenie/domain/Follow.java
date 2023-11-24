package com.ngg.servernewgenie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "follow")
public class Follow implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_num", nullable = false)
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_num", nullable = false)
    private User fromUser;

    public Follow(Long followId, User toUser, User fromUser) {
        this.followId = followId;
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}