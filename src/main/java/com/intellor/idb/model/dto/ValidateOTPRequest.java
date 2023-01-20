package com.intellor.idb.model.dto;

import lombok.Data;

@Data
public class ValidateOTPRequest {
  private String email;
  private String otp;
}
