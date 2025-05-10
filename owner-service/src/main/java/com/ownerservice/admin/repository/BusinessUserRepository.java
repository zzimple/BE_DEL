package com.ownerservice.admin.repository;

import com.ownerservice.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserRepository extends JpaRepository<Admin, String> {
  boolean existsByBusinessNumber(String businessNumber);
}