package com.moin.services.notification.entity;

import lombok.Data;

@Data
public class Mail {

  private String from;
  private String to;
  private boolean isHtml = true;
  private String subject;
  private String body;
  private String notificationEventType;

}
