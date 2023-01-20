package com.intellor.idb.service;

import com.intellor.idb.model.UserModel;
import com.intellor.idb.model.dto.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommonServiceImpl {

  @Autowired
  UserRepoWrapper userRepoWrapper;

  @Autowired
  OTPService otpService;

  @Autowired
  EmailService emailService;

  public Long generateOtp() {
    return UUID.randomUUID().getMostSignificantBits();
  }

  public ValidateResponse generateAndSendOtpEmail(String username){
    ValidateResponse validateResponse = ValidateResponse.builder().build();
    if(otpService.getOtp(username)!=null && !otpService.getOtp(username).isEmpty()){
      validateResponse.setMessage("OTP already generated for "+username);
      validateResponse.setStatus(HttpStatus.CONFLICT.value());
      return validateResponse;
    }
    Long otp = otpService.generateOTP(username);
    emailService.sendNewMail(username,"Recording Dashboard OTP","One Time Password: "+otp+" \n Valid for: 2 Minutes");
    validateResponse.setMessage("OTP Generated for "+username);
    return validateResponse;
  }

  public UserModel saveUser(UserModel userModel) {
    return userRepoWrapper.saveUser(userModel);
  }
}
