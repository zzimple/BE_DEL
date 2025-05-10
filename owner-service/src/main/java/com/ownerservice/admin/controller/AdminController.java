package com.ownerservice.admin.controller;


import com.ownerservice.admin.dto.request.LoginIdCheckRequest;
import com.ownerservice.admin.dto.request.LoginRequest;
import com.ownerservice.admin.dto.request.SignUpRequest;
import com.ownerservice.admin.dto.response.LoginIdCheckResponse;
import com.ownerservice.admin.dto.response.LoginResponse;
import com.ownerservice.admin.dto.response.SignUpResponse;
import com.ownerservice.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminController {

  private final AdminService adminService;

  @Operation(
      summary = "[ 사장님 사용자 | 토큰 X | 로그인 중복 검사 ]",
      description =
          """
           **Parameters**  \n
           loginId: 중복 검사할 로그인 아이디  \n

           **Returns**  \n
           isDuplicate: 중복 여부 (true/false)  \n
           message: 결과 메시지  \n
           """)
  @PostMapping("/login-id-duplicate-check")
  public ResponseEntity<LoginIdCheckResponse> checkLoginIdDuplicate(
      @Parameter(description = "중복 검사할 로그인 아이디") @RequestBody LoginIdCheckRequest request) {
    return adminService.checkLoginIdDuplicate(request);
  }

  @Operation(
      summary = "[ 사장님 사용자 | 토큰 X | 회원가입 ]",
      description =
          """
           **Parameters**  \n
           loginId: 로그인 아이디  \n
           password: 비밀번호  \n
           userName: 사용자 이름  \n
           phoneNumber: 전화번호  \n
           email: 이메일(선택) \n

           **Returns**  \n
           message: 결과 메시지  \n
           isSuccess: 회원가입 성공 여부  \n
           """)
  @PostMapping("/register")
  public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest request) {
    return adminService.register(request);
  }

  @Operation(
      summary = "[ 일반 사용자 | 토큰 X | 로그인 ]",
      description =
          """
          **Parameters**  \n
          loginId: 로그인 아이디  \n
          password: 비밀번호  \n

          **Returns**  \n
          accessToken: JWT 액세스 토큰  \n
          refreshToken: JWT 리프레시 토큰  \n
          message: 결과 메시지  \n
          isSuccess: 로그인 성공 여부  \n
          """)
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    return adminService.login(request);
  }
}
