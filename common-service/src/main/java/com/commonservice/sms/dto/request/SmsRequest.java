package com.commonservice.sms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SMS 발송 Request", description = "사용자에게 메세지 전송을 위한 데이터 전송")
public class SmsRequest {
  @NotEmpty(message = "휴대폰 번호를 입력해주세요")
  @Schema(description = "메세지를 전송할 휴대폰 번호", example = "010-1234-5678")
  private String phoneNum;
}