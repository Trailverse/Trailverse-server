package org.example.trailverse.user;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String loginId;
    private String password;
}
