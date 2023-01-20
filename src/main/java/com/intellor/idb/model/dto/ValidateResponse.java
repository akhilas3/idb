package com.intellor.idb.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intellor.idb.model.HttpResponseModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateResponse extends HttpResponseModel {
  private String message;
  private String authToken;
}
