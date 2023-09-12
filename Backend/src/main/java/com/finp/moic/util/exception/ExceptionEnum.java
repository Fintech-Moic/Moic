package com.finp.moic.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {

    /* 혜지 : CODE 대문자 FIX 필요 */
    // 1. USER (U)
    USER_REGIST_DUPLICATE(HttpStatus.BAD_REQUEST,"U000","이미 존재하는 회원입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
    USER_INVALID(HttpStatus.UNAUTHORIZED,"U002","아이디 혹은 비밀번호가 틀렸습니다."),
    USER_REGIST_ERROR(HttpStatus.BAD_REQUEST,"U003","입력 사항을 확인하세요."),

    // 2. SHOP (S)

    // 3. CARD (C)
    /* 혜지 : 인덱스 테스트용 Exception */
    CARD_LOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"C001","카드를 불러오지 못했습니다."),

    // 4. GIFT CARD (G)

    // 5. CARD BENEFIT (CB)

    // 6. USER BOOKMARK (UB)

    // 7. SERVER ERROR (E)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "서버 에러가 발생했습니다.");


    private final HttpStatus status;
    private final String errorCode;
    private String errorMessage;

    ExceptionEnum(HttpStatus status, String errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
