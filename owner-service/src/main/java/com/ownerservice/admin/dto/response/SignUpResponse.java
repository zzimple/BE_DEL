package com.ownerservice.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게스트 회원가입 Response")
public class SignUpResponse {

  @Schema(description = "회원가입 결과", example = "true")
  private boolean isSuccess;

  @Schema(description = "응답 메세지", example = "회원가입이 완료되었습니다.")
  private String message;
}