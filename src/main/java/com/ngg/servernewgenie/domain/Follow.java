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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_num", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_num", nullable = false)
    private User toUser;

    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}