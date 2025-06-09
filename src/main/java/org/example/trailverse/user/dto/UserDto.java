package org.example.trailverse.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.trailverse.user.domain.User;

@Builder
@Getter
public class UserDto {
    private String userId;
    private String password;

    public static UserDto from(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .password(user.getUserPw())
                .build();
    }

}
