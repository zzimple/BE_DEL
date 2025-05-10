package com.commonservice.sms.service;


import com.commonservice.global.exception.CustomException;
import com.commonservice.global.exception.model.SmsErrorCode;
import com.commonservice.sms.dto.request.SmsRequest;
import com.commonservice.sms.dto.response.SmsResponse;
import com.commonservice.sms.util.SmsCertificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

  private final SmsCertificationUtil smsCertificationUtil;

  @Transactional
  public SmsResponse sendSms(SmsRequest smsRequest) {

    String phoneNum = smsRequest.getPhoneNum();

    try {
      // 인증번호 생성 및 Redis 저장
      String certificationCode = smsCertificationUtil.generateAndSaveCertification(phoneNum);

      String messageContent = "[Zzimple] 인증번호는 [" + certificationCode + "] 입니다.";

      // 로깅
      log.info("본인인증용 SMS 발송 요청 - 번호: {}, 메시지: {}", phoneNum, messageContent);

      // SMS 발송
      smsCertificationUtil.sendSMS(phoneNum, messageContent);

      log.info("SMS 전송 성공 - 번호: {}", phoneNum);

      return SmsResponse.builder()
          .success(true)
          .message("인증번호 전송 성공")
          .build();

    } catch (Exception e) {
      log.error("SMS 전송 실패 - 번호: {}, 에러: {}", phoneNum, e.getMessage());

      throw new CustomException(SmsErrorCode.SMS_SEND_FAILED);
    }
  }
}