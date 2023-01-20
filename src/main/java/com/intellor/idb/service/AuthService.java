package com.intellor.idb.service;

import com.intellor.idb.exception.IdbException;
import com.intellor.idb.model.dto.ValidateOTPRequest;
import com.intellor.idb.util.IdbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  OTPService otpService;
  public void authenticateOTP(ValidateOTPRequest authenticationRequest) throws IdbException {
    try {
      if (!authenticationRequest.getOtp().equals(otpService.getOtp(authenticationRequest.getEmail()))){
        throw new IdbException(HttpStatus.FORBIDDEN.getReasonPhrase());
      }
    } catch (final BadCredentialsException ex) {
      throw new IdbException(IdbConstants.EXCEPTION_VALIDATE_FAILED);
    }
  }


}
