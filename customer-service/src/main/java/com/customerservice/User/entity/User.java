package com.customerservice.User.entity;

import com.commonservice.common.entity.BaseUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder  // BaseUser의 필드까지 포함하는 빌더 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 protected no-arg 생성자
public class User extends BaseUser {
}