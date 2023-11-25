package com.ngg.servernewgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long user_id;

    private String user_name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    @JsonIgnore
    private User user;


    public void setUser(User user){
        this.user = user;
    }
}


//@Entity
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Authority {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonIgnore
//    private Long authorityId;
//
//    private String userName;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_num", referencedColumnName = "userNum")
//    @JsonIgnore
//    private User user;
//
//    public void setUser(User user){
//        this.user = user;
//    }
//}