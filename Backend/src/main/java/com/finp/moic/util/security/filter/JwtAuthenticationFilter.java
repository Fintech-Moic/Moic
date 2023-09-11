package com.finp.moic.util.security.filter;

import com.finp.moic.user.model.entity.User;
import com.finp.moic.util.security.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private static final String BEARER_TYPE = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("리퀘 : " + request);
        String token = parseBearerToken(request);
        System.out.println("토큰 : " + token);
        User user = parseUserSpecification(token);
        AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user,token,null);
        authenticated.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        System.out.println("헤더에 있는 토큰값 : " + request.getHeader(AUTHORIZATION));
        String authorization = request.getHeader(AUTHORIZATION);
        if(authorization!=null && authorization.startsWith(BEARER_TYPE)){
            System.out.println("이거 반환");
            return authorization.substring(BEARER_TYPE.length());
        }
        System.out.println("실패ㅠㅠ");
        return authorization;
//        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
//                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer"))
//                .map(token -> token.substring(7))
//                .orElse(null);
    }

    private User parseUserSpecification(String token) {

        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 1)
                .map(jwtProvider::getSubject)
                .orElse("anonymous:anonymous")
                .split(":");
        System.out.println("필터1: " + split[0]);
        System.out.println("필터2: " + split[1]);
        return new User(null, split[0], "",split[1]);
//        List.of(new SimpleGrantedAuthority(split[1]))
    }

}
