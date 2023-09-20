package com.finp.moic.util.cookie;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public Cookie createCookie(String refreshToken){
        String cookieName = "refreshToken";
        String cookieValue = refreshToken;

        Cookie cookie = new Cookie(cookieName,cookieValue);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        //30일
        cookie.setMaxAge(60*60*24*30);
        return cookie;
    }

}
