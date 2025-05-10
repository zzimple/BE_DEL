package com.commonservice.sms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "휴대폰 인증 Response")
public class PhoneVerificationResponse {
  @Schema(description = "인증번호 전송 결과", example = "true")
  private boolean verified;

  @Schema(description = "응답 메세지", example = "본인 인증이 완료되었습니다.")
  private String message;
}
