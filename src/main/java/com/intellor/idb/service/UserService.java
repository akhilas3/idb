package com.intellor.idb.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

  public UserDetails buildUser(String userName, String password){
    return new org.springframework.security.core.userdetails.User(userName, new BCryptPasswordEncoder().encode(password), new ArrayList<>());
  }

}
