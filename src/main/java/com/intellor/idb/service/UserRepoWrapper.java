package com.intellor.idb.service;

import com.intellor.idb.model.UserModel;
import com.intellor.idb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepoWrapper {

  @Autowired
  private UserRepository userRepository;

  public UserModel findByUsername(String username){
    return userRepository.findOneByUsername(username).get();
  }

  public UserModel saveUser(UserModel userModel){
    return userRepository.save(userModel);
  }

}
