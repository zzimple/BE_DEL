package com.ownerservice.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "사장님 회원가입 Request")
public class SignUpRequest {
  @Schema(description = "사업자번호(아이디)", example = "1234567890")
  private String b_no;

  @Schema(description = "비밀번호")
  private String password;

  @Schema(description = "이름", example = "김짐플")
  private String userName;

  @Schema(description = "전화번호", example = "010-0000-0000")
  private String phoneNumber;

  @Schema(description = "이메일", example = "zzimple.official@gmail.com")
  private String email;
}
