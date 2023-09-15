package com.ngg.servernewgenie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "follow")
@NoArgsConstructor
public class Follow implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, name = "f_id")
    private Long f_id;

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_num", nullable = false)
    private User to_user_id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_num", nullable = false)
    private User from_user_id;

    public Follow(Long f_id, User to_user_id, User from_user_id) {
        this.f_id = f_id;
        this.to_user_id = to_user_id;
        this.from_user_id = from_user_id;
    }
}