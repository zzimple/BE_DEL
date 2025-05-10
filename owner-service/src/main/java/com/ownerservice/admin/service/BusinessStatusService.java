package com.ownerservice.admin.service;

import com.ownerservice.admin.dto.request.BusinessStatusRequest;
import com.ownerservice.admin.dto.response.BusinessStatusResponse;
import com.ownerservice.admin.entity.Admin;
import com.ownerservice.admin.repository.BusinessUserRepository;
import com.commonservice.global.exception.CustomException;
import com.commonservice.global.exception.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BusinessStatusService {

  @Value("${api.serviceKey}")
  private String serviceKey;

  private final String API_URL = "https://api.odcloud.kr/api/nts-businessman/v1/status";
  private final OkHttpClient client = new OkHttpClient();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final BusinessUserRepository businessUserRepository;

  public String checkBusinessStatus(BusinessStatusRequest requestDTO) {
    String jsonRequest;
    try {
      // DTO를 JSON 문자열로 직렬화
      jsonRequest = objectMapper.writeValueAsString(requestDTO);
    } catch (IOException e) {
      // 직렬화 실패 시 잘못된 입력으로 간주
      throw new CustomException(GlobalErrorCode.INVALID_INPUT_VALUE);
    }

    RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json; charset=utf-8"));
    String fullUrl = API_URL + "?serviceKey=" + serviceKey;

    Request request = new Request.Builder()
        .url(fullUrl)
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .post(body)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
      }

      // body가 null 방지
      ResponseBody responseBodyRaw = response.body();
      if (responseBodyRaw == null) {
        throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
      }

      String responseBody = responseBodyRaw.string();
      BusinessStatusResponse result = objectMapper.readValue(responseBody, BusinessStatusResponse.class);

      // 데이터 없을 경우 N 반환
      if (result.getData() == null || result.getData().isEmpty()) {
        return "N";
      }

      for (BusinessStatusResponse.BusinessData data : result.getData()) {
        String bNo = data.getB_no();
        String status = data.getB_stt();

        // "계속사업자"가 아닌 경우 N 반환
        if (!"계속사업자".equals(status)) {
          return "N";
        }

        // DB에 없는 사업자등록번호면 새 엔티티로 저장
//        if (!businessUserRepository.existsByBusinessNumber(bNo)) {
//          Admin profile = new Admin();
//          profile.setBusinessNumber(bNo);
//          businessUserRepository.save(profile);
//        }
      }
      return "Y";
    } catch (IOException e) {
      throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

}

