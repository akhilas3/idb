package com.intellor.idb.config;

import com.google.gson.Gson;
import com.intellor.idb.model.HttpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPointConfig extends BasicAuthenticationEntryPoint {

  @Autowired
  private Gson gson;

  @Override
  public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException {
    response.addHeader("WWW-Authenticate","Basic Realm - "+getRealmName());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader(HttpHeaders.CONTENT_TYPE,"application/json");
    PrintWriter writer = response.getWriter();
    HttpResponseModel httpResponseModel = new HttpResponseModel();
    httpResponseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
    httpResponseModel.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    writer.print(gson.toJson(httpResponseModel));
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("Intellor");
    super.afterPropertiesSet();
  }
}
