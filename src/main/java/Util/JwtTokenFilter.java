package Util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.trailverse.user.domain.User;
import org.example.trailverse.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final SecretKey secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.split(" ")[1];

        if (JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        String loginId = JwtTokenUtil.getLonginId(token, secretKey);
        User loginUser = userService.findUserId(loginId); // 메서드명 확인

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserId(),
                null,
                List.of(new SimpleGrantedAuthority("USER"))
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if(authorizationHeader == null){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        if(!authorizationHeader.startsWith("Bearer")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorizationHeader.split("")[1];
//
//        if(JwtTokenUtil.isExpired(token,secretKey)){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String loginId = JwtTokenUtil.getLonginId(token,secretKey);
//        User loginUser =  userService.findUserId(loginId);
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUserId(),null, List.of(new SimpleGrantedAuthority("USER")));
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        filterChain.doFilter(request, response);
//    }
}
