package com.intellor.idb.config;

import com.intellor.idb.service.JwtTokenService;
import com.intellor.idb.service.OTPService;
import com.intellor.idb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  JwtTokenService jwtTokenService;

  @Autowired
  UserService userService;

  @Autowired
  OTPService otpService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                  final FilterChain chain) throws ServletException, IOException {
    // look for Bearer auth header
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    final String token = header.substring(7);
    final String username = jwtTokenService.validateTokenAndGetUsername(token);
    if (username == null) {
      // validation failed or token expired
      chain.doFilter(request, response);
      return;
    }

    // set user details on spring security context
    final UserDetails userDetails = userService.buildUser(username, otpService.getOtp(username));
    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // continue with authenticated user
    chain.doFilter(request, response);
  }

}