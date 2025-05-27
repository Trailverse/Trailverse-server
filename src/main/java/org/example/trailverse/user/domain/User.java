package org.example.trailverse.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userPw;
    @Column(nullable = false)
    private String userNickname;

    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setUserPw(String userPw){
        this.userPw=userPw;
    }
    public void setUserNickname(String userNickname){
        this.userNickname = userNickname;
    }

//    public User(String userId, String userPw, String userNickname){
//        this.userId= userId;
//        this.userPw = userPw;
//        this.userNickname = userNickname;
//    }
}
