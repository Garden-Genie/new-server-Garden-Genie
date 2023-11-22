package com.ngg.servernewgenie.domain;

import lombok.*;
import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num", nullable = false)
    private Long userNum;

    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

    @Column(name = "user_email", length = 50, nullable = false, unique = true)
    private String userEmail;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();


    public User(Long userNum) {
        this.userNum = userNum;
    }

    public void setRoles(List<Authority> role){
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }

    public User(String user_id, String user_name, String user_pwd, String user_email){
        this.userId = user_id;
        this.userName = user_name;
        this.userPwd = user_pwd;
        this.userEmail = user_email;
    }
}