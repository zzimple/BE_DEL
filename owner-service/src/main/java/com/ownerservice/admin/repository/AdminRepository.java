package com.ownerservice.admin.repository;

import com.ownerservice.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
  Optional<Admin> findByLoginId(String loginId);

  // 사업자등록번호로 조회
  Optional<Admin> findByBusinessNumber(String businessNumber);

  // 사업자등록번호 중복 체크
  boolean existsByBusinessNumber(String businessNumber);
}
