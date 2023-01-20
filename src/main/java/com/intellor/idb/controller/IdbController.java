package com.intellor.idb.controller;

import com.intellor.idb.exception.IdbException;
import com.intellor.idb.service.*;
import com.intellor.idb.model.UserModel;
import com.intellor.idb.model.dto.OtpGenerateRequest;
import com.intellor.idb.model.dto.ValidateOTPRequest;
import com.intellor.idb.model.dto.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class IdbController {

  @Autowired
  CommonServiceImpl commonService;

  @Autowired
  AuthService authService;

  @Autowired
  OTPService otpService;

  @Autowired
  UserService userService;

  @Autowired
  S3BucketStorageService s3BucketStorageService;

  @Autowired
  JwtTokenService jwtTokenService;

  @PostMapping({"/send-otp-email"})
  public ResponseEntity<?> getTestName(@Valid @RequestBody OtpGenerateRequest otpGenerateRequest) {
    ValidateResponse validateResponse = commonService.generateAndSendOtpEmail(otpGenerateRequest.getEmail());
    return new ResponseEntity<>(validateResponse, HttpStatus.valueOf(validateResponse.getStatus()));
  }

  @PostMapping({"/save-user"})
  public UserModel saveUser(@RequestBody UserModel userModel) {
    return commonService.saveUser(userModel);
  }

  @GetMapping({"/get-otp"})
  public String getOtp(@RequestParam("email") String email) {
    return otpService.getOtp(email);
  }

  @GetMapping({"/get-recordings"})
  public ResponseEntity<?> getRecordings(@RequestParam("eventId") String eventId) {
    return new ResponseEntity<>(s3BucketStorageService.listFiles(),HttpStatus.OK);
  }



  @PostMapping("/validate-otp")
  public ResponseEntity<?> authenticate(@RequestBody final ValidateOTPRequest authenticationRequest) {
    ValidateResponse authenticationResponse = ValidateResponse.builder().build();
    try {
      authService.authenticateOTP(authenticationRequest);
      final UserDetails userDetails = userService.buildUser(authenticationRequest.getEmail(), authenticationRequest.getOtp());
      authenticationResponse.setMessage(HttpStatus.OK.name());
      authenticationResponse.setAuthToken(jwtTokenService.generateToken(userDetails));
    } catch (IdbException e) {
      authenticationResponse.setStatus(HttpStatus.FORBIDDEN.value());
      authenticationResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(authenticationResponse,HttpStatus.valueOf(authenticationResponse.getStatus()));
  }

}
