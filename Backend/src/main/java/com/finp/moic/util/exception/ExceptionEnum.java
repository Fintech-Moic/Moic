package com.finp.moic.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    /* 혜지 : CODE 대문자 FIX 필요 */
    // 1. USER (U)
    USER_REGIST_DUPLICATE(HttpStatus.BAD_REQUEST,"U0001","이미 존재하는 회원입니다."),

    // 2. USER SECURITY (US)

    // 3. SHOP (S)

    // 4. CARD (C)
    /* 혜지 : 인덱스 테스트용 Exception */
    CARD_LOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"C0001","카드를 불러오지 못했습니다.");

    // 5. GIFT CARD (G)

    // 6. CARD BENEFIT (CB)

    // 7. USER BOOKMARK (UB)

    // 8. USER CARD (UC)


    private final HttpStatus status;
    private final String errorCode;
    private String errorMessage;
}
