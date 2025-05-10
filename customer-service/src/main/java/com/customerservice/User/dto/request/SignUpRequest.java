package com.customerservice.User.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "게스트 회원가입 Request")
public class SignUpRequest {
  @Schema(description = "아이디", example = "test01")
  private String loginId;

  @Schema(description = "비밀번호")
  private String password;

  @Schema(description = "이름", example = "김짐플")
  private String userName;

  @Schema(description = "전화번호", example = "010-0000-0000")
  private String phoneNumber;

  @Schema(description = "이메일", example = "zzimple.official@gmail.com")
  private String email;
}
