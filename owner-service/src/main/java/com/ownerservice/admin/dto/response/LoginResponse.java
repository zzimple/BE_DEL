package com.ownerservice.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

  @Schema(description = "JWT 액세스 토큰")
  private String accessToken; // JWT 액세스 토큰

  @Schema(description = "JWT 리프레시 토큰")
  private String refreshToken; // JWT 리프레시 토큰 (필요한 경우)

  @Schema(description = "로그인 결과", example = "true")
  private boolean isSuccess;

  @Schema(description = "응답 메세지", example = "로그인에 성공하였습니다.")
  private String message;
}
