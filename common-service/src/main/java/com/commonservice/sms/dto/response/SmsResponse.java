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
@Schema(title = "SMS 발송 Response", description = "사용자에게 메세지 전송에 대한 응답 반환")
public class SmsResponse {
  @Schema(description = "메시지 전송 성공 여부")
  private boolean success;

  @Schema(description = "응답 메시지")
  private String message;
}
