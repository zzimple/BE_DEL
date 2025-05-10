package com.ownerservice.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginIdCheckResponse {
  @Schema(description = "아이디 중복 검사 결과", example = "true")
  private boolean isDuplicate;

  @Schema(description = "응답 메세지", example = "사용 가능한 이메일입니다.")
  private String message;
}
