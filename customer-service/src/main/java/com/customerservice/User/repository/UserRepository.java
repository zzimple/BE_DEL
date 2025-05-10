package com.customerservice.User.repository;

import com.customerservice.User.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  // 로그인 아이디로 찾기
  Optional<User> findByLoginId(String loginId);
}