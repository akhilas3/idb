package com.intellor.idb.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class OtpGenerateRequest {
  @NotNull
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
          flags = Pattern.Flag.CASE_INSENSITIVE)
  private String email;
}
