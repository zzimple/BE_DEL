package com.commonservice.sms.util;

import com.commonservice.global.exception.CustomException;
import com.commonservice.global.exception.model.SmsErrorCode;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SmsCertificationUtil {

  private final RedisTemplate<String, String> redisTemplate;

  @Value("${coolsms.api.key}") // coolsms의 API 키 주입
  private String apiKey;

  @Value("${coolsms.api.secret}") // coolsms의 API secret key 주입
  private String apiSecret;

  @Value("${coolsms.phoneNumber}") // 발신자 번호 주입
  private String fromNumber;

  DefaultMessageService messageService; // 메시지 서비스를 위한 객체

  public SmsCertificationUtil(
      @Qualifier("customStringRedisTemplate") RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct // 의존성 주입이 완료된 후 초기화를 수행하는 메서드
  public void init() {
    this.messageService =
        NurigoApp.INSTANCE.initialize(
            apiKey, apiSecret, "https://api.coolsms.co.kr"); // 메시지 서비스 초기화
  }

  // 인증번호 생성
  private String createRandomCode() {
    return String.valueOf(100000 + new Random().nextInt(900000));
  }

  /** 키 생성 유틸 */
  private String buildKey(String phoneNumber) {                          // 수정
    return "VERIF_" + phoneNumber;                                       // 수정
  }

  // ✅ 인증번호 생성 + Redis 저장
  public String generateAndSaveCertification(String phoneNumber) {
    String code = createRandomCode();
    String key = buildKey(phoneNumber);
    redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES); // 3분 유효
    return code;
  }

  // 단일 메시지 발송
  public void sendSMS(String to, String certificationCode) {
    Message message = new Message(); // 새 메시지 객체 생성
    message.setFrom(fromNumber); // 발신자 번호 설정
    message.setTo(to); // 수신자 번호 설정
    message.setText(certificationCode); // 메시지 내용 설정

    this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 메시지 발송 요청
  }


  // ✅ Redis에서 코드 꺼내 검증 메소드 추가
  public void verifyCertification(String phoneNumber, String code) {     // 수정: 메소드 추가
    String key = buildKey(phoneNumber);                                  // 수정
    String saved = redisTemplate.opsForValue().get(key);                 // 수정

    if (saved == null || !saved.equals(code)) {                          // 수정
      throw new CustomException(SmsErrorCode.SMS_SEND_FAILED);             // 수정
    }

    redisTemplate.delete(key);                                           // 수정: 인증 후 키 삭제
  }
}