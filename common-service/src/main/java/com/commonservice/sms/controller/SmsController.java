package com.commonservice.sms.controller;

import com.commonservice.global.dto.BaseResponse;
import com.commonservice.sms.dto.request.PhoneVerificationRequest;
import com.commonservice.sms.dto.request.SmsRequest;
import com.commonservice.sms.dto.response.PhoneVerificationResponse;
import com.commonservice.sms.dto.response.SmsResponse;
import com.commonservice.sms.service.PhoneVerificationService;
import com.commonservice.sms.service.SmsService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {

  private final SmsService smsService;
  private final PhoneVerificationService phoneService;

  @PostMapping("/send-message")
  public ResponseEntity<BaseResponse<SmsResponse>> SendSMS(@RequestBody @Valid SmsRequest request) {
    SmsResponse smsResponse = smsService.sendSms(request);
    return ResponseEntity.ok(BaseResponse.success("발송 성공", smsResponse));
  }

  @PostMapping("/verify-code")
  public ResponseEntity<BaseResponse<PhoneVerificationResponse>> VerifyCode(
      @RequestBody @Valid PhoneVerificationRequest request) {

    phoneService.verifyCode(
        request.getPhoneNumber(),
        request.getCode()
    );

    PhoneVerificationResponse response = PhoneVerificationResponse.builder()
        .verified(true)
        .message("본인 인증이 완료되었습니다.")
        .build();

    return ResponseEntity.ok(
        BaseResponse.success("인증 성공", response));
  }
}