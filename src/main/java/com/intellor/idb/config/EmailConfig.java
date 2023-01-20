package com.intellor.idb.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig
{
  @Bean
  public JavaMailSender getJavaMailSender()
  {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.mailtrap.io");
    mailSender.setPort(2525);

//    mailSender.setUsername("testotpsendqb@gmail.com");
    mailSender.setUsername("d1f91ef45d069e");
    mailSender.setPassword("6883eff1a9df5d");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }

  @Bean
  public SimpleMailMessage emailTemplate()
  {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("akhilisonlinenow@gmail.com");
    message.setFrom("someone@gmail.com");
    message.setText("FATAL - Application crash. Save your job !!");
    return message;
  }
}