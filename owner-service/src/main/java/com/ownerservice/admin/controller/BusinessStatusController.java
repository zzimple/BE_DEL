package com.ownerservice.admin.controller;

import com.ownerservice.admin.dto.request.BusinessStatusRequest;
import com.ownerservice.admin.exception.BusinessErrorCode;
import com.ownerservice.admin.service.BusinessStatusService;
import com.commonservice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/business")
@RequiredArgsConstructor
public class BusinessStatusController {

  private final BusinessStatusService businessStatusService;

  @PostMapping("/verify")
  public ResponseEntity<String> verifyBusiness(@RequestBody BusinessStatusRequest request) {
    if (request.getB_no() == null || request.getB_no().isEmpty()) {
      throw new CustomException(BusinessErrorCode.BUSINESS_NUMBER_MISSING);
    }

    String result = businessStatusService.checkBusinessStatus(request);

    if (!"Y".equals(result)) {
      throw new CustomException(BusinessErrorCode.BUSINESS_NUMBER_INVALID);
    }

    return ResponseEntity.ok("Y");
  }
}