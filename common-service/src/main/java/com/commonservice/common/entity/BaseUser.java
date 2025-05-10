package com.commonservice.common.entity;

import com.commonservice.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder                              // ← @Builder 제거, SuperBuilder만
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class BaseUser extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_name", nullable = false)
  private String userName; // 이름

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber; // 전화번호

  @Column(name = "email", unique = true)
  private String email; // 이메일

  @Column(name = "login_id", nullable = false, unique = true)
  private String loginId; // 아이디

  @Column(name = "password")
  private String password;  // 비밀번호

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRole role = UserRole.CUSTOMER; // 기본값 GUEST

  @Column(name = "refresh_token")
  private String refreshToken;
}
