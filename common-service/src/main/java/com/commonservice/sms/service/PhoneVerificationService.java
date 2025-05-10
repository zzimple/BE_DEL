package com.commonservice.sms.service;


import com.commonservice.sms.dto.request.PhoneVerificationRequest;
import com.commonservice.sms.util.SmsCertificationUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneVerificationService {

  private final SmsCertificationUtil smsCertificationUtil;

  @Transactional
  public void verifyCode(String phoneNum, String code) {
    // 수정: util.verifyCertification 호출
    smsCertificationUtil.verifyCertification(phoneNum, code);
  }
}
