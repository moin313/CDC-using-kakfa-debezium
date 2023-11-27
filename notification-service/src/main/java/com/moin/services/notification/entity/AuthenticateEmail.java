//package com.moin.services.notification.entity;
//
//import javax.mail.Authenticator;
//import javax.mail.PasswordAuthentication;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticateEmail extends Authenticator {
//  @Value("${sender.email}")
//  private String email;
//
//  @Value("${sender.password}")
//  private String password;
//  
//  public PasswordAuthentication getPasswordAuthentication() {
//      return new PasswordAuthentication(email, password);
//  }
//}