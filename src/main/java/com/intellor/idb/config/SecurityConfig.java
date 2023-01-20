package com.intellor.idb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  JwtRequestFilter jwtRequestFilter;

  @Value("${idb.default.username:idb-user}")
  private String defaultUsername;

  @Value("${idb.default.password:idb-user}")
  private String defaultPassword;


  @Autowired
  private AuthenticationEntryPointConfig authenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
          final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


  @Bean
  protected InMemoryUserDetailsManager configAuthentication() {
    List<UserDetails> users = new ArrayList<>();
    List<GrantedAuthority> adminAuthority = new ArrayList<>();
    adminAuthority.add(new SimpleGrantedAuthority("ADMIN"));
    UserDetails admin = new User(defaultUsername, passwordEncoder().encode(defaultPassword), adminAuthority);
    users.add(admin);
    return new InMemoryUserDetailsManager(users);
  }

  @Bean
  public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
    http.cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/get-recordings").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint);

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
