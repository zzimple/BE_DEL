package com.ownerservice.admin.service;

import com.commonservice.global.security.JwtUtil;
import com.ownerservice.admin.dto.request.LoginIdCheckRequest;
import com.ownerservice.admin.dto.request.LoginRequest;
import com.ownerservice.admin.dto.request.SignUpRequest;
import com.ownerservice.admin.dto.response.LoginIdCheckResponse;
import com.ownerservice.admin.dto.response.LoginResponse;
import com.ownerservice.admin.dto.response.SignUpResponse;
import com.ownerservice.admin.entity.Admin;
import com.ownerservice.admin.repository.AdminRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public ResponseEntity<LoginIdCheckResponse> checkLoginIdDuplicate(LoginIdCheckRequest request) {
    // 로그인 아이디(이메일) 존재 여부 확인
    boolean isDuplicate = adminRepository.findByLoginId(request.getLoginId()).isPresent();

    // 응답 생성
    LoginIdCheckResponse response =
        LoginIdCheckResponse.builder()
            .isDuplicate(isDuplicate)
            .message(isDuplicate ? "이미 사용 중인 아아디입니다." : "사용 가능한 아이디입니다.")
            .build();

    return ResponseEntity.ok(response);
  }

  @Transactional
  public ResponseEntity<SignUpResponse> register(SignUpRequest request) {
    try {
      // 1. 중복 회원 검사
      if (adminRepository.findByLoginId(request.getB_no()).isPresent()) {
        log.warn("[POST /api/admin/register] 회원가입 실패 - 이미 존재하는 사업자 번호 {}", request.getB_no());
        return ResponseEntity.ok(
            SignUpResponse.builder().isSuccess(false).message("이미 존재하는 사업자번호입니다.").build());
      }

      // 2. 비밀번호 암호화
      String encodedPassword = passwordEncoder.encode(request.getPassword());

      // 3. 사용자 엔티티 생성
      Admin admin =
          Admin.builder()
              .userName(request.getUserName())
              .phoneNumber(request.getPhoneNumber())
              .businessNumber(request.getB_no())
              .password(encodedPassword)
              .email(request.getEmail())
              .build();

      // 4. 데이터베이스에 저장
      adminRepository.save(admin);

      log.info(
          "[POST /api/admin/register] 회원가입 성공 - ID: {}, 이름: {}",
          admin.getLoginId(),
          admin.getUserName());
      // 5. 성공 응답 반환
      return ResponseEntity.ok(
          SignUpResponse.builder().isSuccess(true).message("회원가입이 완료되었습니다.").build());

    } catch (Exception e) {
      // 6. 실패 응답 반환
      log.error("[POST /api/admin/register] 회원가입 실패 - ID: {}", request.getB_no());
      return ResponseEntity.ok(
          SignUpResponse.builder().isSuccess(false).message("회원가입 처리 중 오류가 발생했습니다.").build());
    }
  }

  // 로그인
  @Transactional
  public ResponseEntity<LoginResponse> login(LoginRequest request) {
    try {
      // 1. 로그인 아이디로 사용자 찾기
      Admin admin = adminRepository
          .findByLoginId(request.getLoginId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

      // 2. 비밀번호 확인
      if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
        return ResponseEntity.ok(
            LoginResponse.builder().isSuccess(false).message("잘못된 비밀번호입니다.").build());
      }

      // 3. 토큰 생성
      String accessToken = jwtUtil.createAccessToken(
          admin.getLoginId(),
          List.of(admin.getRole().name())
      );
      String refreshToken = jwtUtil.createRefreshToken(admin.getLoginId());

      // 4. refreshToken DB에 저장
      admin.setRefreshToken(refreshToken);
      adminRepository.save(admin);

      // 5. 로그인 성공 응답 생성
      log.info(
          "[POST /api/admin/login] 로그인 성공 - ID: {} Name: {}",
          admin.getLoginId(),
          admin.getUserName());
      return ResponseEntity.ok(
          LoginResponse.builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .isSuccess(true)
              .message("로그인에 성공하였습니다.")
              .build());

    } catch (IllegalArgumentException e) {
      // 6. 존재하지 않는 아이디인 경우
      log.warn("[POST /api/admin/login] 로그인 실패 - 잘못된 아이디: {}", request.getLoginId());
      return ResponseEntity.ok(
          LoginResponse.builder().isSuccess(false).message(e.getMessage()).build());
    } catch (Exception e) {
      // 7. 기타 예외 처리
      log.error(
          "[POST /api/admin/login] 로그인 오류 발생 - ID: {}, 에러: {}",
          request.getLoginId(),
          e.getMessage());
      return ResponseEntity.ok(
          LoginResponse.builder().isSuccess(false).message("로그인 처리 중 서버 오류 발생." + e).build());
    }
  }
}