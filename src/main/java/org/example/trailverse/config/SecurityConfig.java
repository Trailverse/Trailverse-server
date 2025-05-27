package org.example.trailverse.config;

import Util.JwtTokenFilter;
import Util.JwtTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.trailverse.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
         return httpSecurity
                 .httpBasic().disable()
                 .csrf().disable()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(auth->auth
                 .requestMatchers("/login","/signup").permitAll()
                 .requestMatchers("/jwt-login/admin/**").hasAuthority("ADMIN")
                 .anyRequest().permitAll()
                 )
                 .build();

    }
}
