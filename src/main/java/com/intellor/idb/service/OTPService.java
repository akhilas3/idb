package com.intellor.idb.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

  private final LoadingCache<String, String> otpCache;


  public OTPService(@Value("${idb.otp.expiry.seconds:60}") Long otpExpirySeconds){
    CacheLoader<String, String> loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return "";
      }
    };
    this.otpCache = CacheBuilder.newBuilder()
            .expireAfterWrite(otpExpirySeconds, TimeUnit.SECONDS)
            .build(loader);
  }

  public Long

  generateOTP(String key) {
    Random random = new Random();
    Long otp = (long) (100000 + random.nextInt(900000));
    otpCache.put(key, otp+"");
    return otp;
  }

  public String getOtp(String key) {
    try {
      return otpCache.get(key);
    } catch (Exception e) {
      return "";
    }
  }

  public void clearOTP(String key) {
    otpCache.invalidate(key);
  }
}