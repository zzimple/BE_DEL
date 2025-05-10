package com.customerservice.User.controller;

import com.customerservice.User.dto.request.LoginIdCheckRequest;
import com.customerservice.User.dto.request.LoginRequest;
import com.customerservice.User.dto.request.SignUpRequest;
import com.customerservice.User.dto.response.LoginIdCheckResponse;
import com.customerservice.User.dto.response.SignUpResponse;
import com.customerservice.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {
  private final UserService userService;

  @Operation(
      summary = "[ 일반 사용자 | 토큰 X | 로그인 중복 검사 ]",
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
    return userService.checkLoginIdDuplicate(request);
  }

  @Operation(
      summary = "[ 일반 사용자 | 토큰 X | 회원가입 ]",
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
  public ResponseEntity<SignUpResponse> register(@RequestBody @Valid SignUpRequest request) {
    return userService.register(request);
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
  public ResponseEntity<?> login(
      @Parameter(description = "로그인 정보") @RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }
}