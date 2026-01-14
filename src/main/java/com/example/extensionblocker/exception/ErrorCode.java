package com.example.extensionblocker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_EXTENSION(HttpStatus.CONFLICT, "이미 존재하는 확장자입니다."),
    EXTENSION_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 확장자입니다."),
    NO_CUSTOM_EXTENSION(HttpStatus.NOT_FOUND, "커스텀 확장자가 없습니다."),
    MAX_CUSTOM_EXTENSION_EXCEEDED(HttpStatus.CONFLICT, "200개 이상의 커스텀 확장자를 등록할 수 없습니다."),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");

    private HttpStatus httpStatus;
    private String message;
}
