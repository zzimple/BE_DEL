package com.ownerservice.admin.entity;


import com.commonservice.common.entity.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Admin")
@Getter
@Setter
@SuperBuilder  // BaseUser의 필드까지 포함하는 빌더 생성
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // JPA용 protected n
public class Admin extends BaseUser {

  @Column(name = "business_number", nullable = false, unique = true)
  private String businessNumber;

    @Column(name = "insured")
//  @Column(name = "insured", nullable = false)
  private Boolean insured;

  @Column(name = "tax_type")
  private String taxType;

  @Column(name = "status")
  private String status;
}