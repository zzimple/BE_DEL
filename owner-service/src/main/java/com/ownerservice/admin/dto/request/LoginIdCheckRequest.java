package com.ownerservice.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginIdCheckRequest {
  @Schema(description = "아이디", example = "test01")
  private String loginId;
}