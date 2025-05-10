package com.ownerservice.admin.exception;

import com.commonservice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements BaseErrorCode {

  BUSINESS_NUMBER_MISSING("B001", "사업자등록번호가 누락되었습니다.", HttpStatus.BAD_REQUEST),
  BUSINESS_NUMBER_INVALID("B002", "유효하지 않은 사업자등록번호입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;

}