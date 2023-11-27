package com.moin.services.notification.event;

import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;

@Service
public interface NotificationEventService {
  public void generateNotificationEvent(JsonObject payload);
}