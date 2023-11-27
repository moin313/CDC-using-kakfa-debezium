package com.moin.services.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moin.services.enums.EnumMaster.KafkaOperation;
import com.moin.services.notification.event.NotificationEventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaUtil {
  
  @Autowired
  private NotificationEventService notificationEventService;
  
  public JsonObject getPayloadAndPerformCreateOperationEvents(String kafkaJson) {
    JsonObject jsonObject = new Gson().fromJson(kafkaJson, JsonObject.class);
    JsonObject payload = jsonObject.get("payload").getAsJsonObject();
    
    log.info("convertedObject message:{}", jsonObject);

    if (payload.get("op").getAsString().equals(KafkaOperation.CREATE.getValue())) {
        notificationEventService.generateNotificationEvent(payload);
    }
    return payload;
  }
}
