package com.finp.moic.util.security.filter;

import com.finp.moic.util.security.dto.JwtAuthenticationToken;
import com.finp.moic.util.security.dto.UserAuthentication;
import com.finp.moic.util.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private static final String BEARER_TYPE = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = parseBearerToken(request);
        if(token!=null){
            System.out.println("token : " + token);
            jwtService.validateToken(token);
            UserAuthentication userAuthentication = parseUserSpecification(token);
            List<String> roles = Arrays.asList("USER");
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
    //        AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userAuthentication,token,null);
            Authentication authentication = new JwtAuthenticationToken(userAuthentication,"", authorities);
    //        authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        if(authorization!=null && authorization.startsWith(BEARER_TYPE)){
            return authorization.substring(BEARER_TYPE.length());
        }
        return authorization;
//        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
//                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer"))
//                .map(token -> token.substring(7))
//                .orElse(null);
    }

    private UserAuthentication parseUserSpecification(String token) {

        String id = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 1)
                .map(jwtService::getSubject)
                .orElse("null");
        System.out.println("id : " + id);
        return new UserAuthentication(id);
    }

}
