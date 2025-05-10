package com.commonservice.global.exception;


import com.commonservice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SmsErrorCode implements BaseErrorCode {

  SMS_SEND_FAILED("S001", "SMS 전송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus status;

}