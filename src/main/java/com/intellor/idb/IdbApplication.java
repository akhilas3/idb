package com.intellor.idb;

import com.intellor.idb.model.UserModel;
import com.intellor.idb.service.UserRepoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IdbApplication implements CommandLineRunner {

  @Autowired
  UserRepoWrapper userRepository;

  public static void main(String[] args) {
    SpringApplication.run(IdbApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();
    userRepository.saveUser(new UserModel("akhil", new BCryptPasswordEncoder().encode("as")));
  }

}
