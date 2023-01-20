package com.intellor.idb.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;

@Data
public class HttpResponseModel {
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
  private Date timestamp = new Date();
  private Integer status = HttpStatus.OK.value();
  private String message = HttpStatus.OK.toString();
}
