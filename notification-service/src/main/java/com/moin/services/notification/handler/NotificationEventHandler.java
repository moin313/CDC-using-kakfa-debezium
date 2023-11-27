package com.moin.services.notification.handler;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.moin.services.enums.EnumMaster.NotificationEventType;
import com.moin.services.enums.EnumMaster.NotificationStatus;
import com.moin.services.notification.entity.NotificationEvent;
import com.moin.services.user.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationEventHandler {

  public NotificationEvent generateNotificationEvent(User user,
      NotificationStatus notificationStatus, NotificationEventType notificationEventType,
      Date lastRecordFetchDate, String operation) {

    log.info("generateNotificationEvent() Handler entered table:{} |notificationEventType:{}",
        notificationEventType);

    NotificationEvent notificationEvent = new NotificationEvent();
    try {
      notificationEvent.setUserId(user.getId());
      notificationEvent.setUsername(user.getUserName());
      notificationEvent.setRoleId(user.getRole().getRoleId());
      notificationEvent.setRoleName(user.getRole().name());
      notificationEvent
          .setNotificationEventTypeId(notificationEventType.getNotificationEventTypeId());
      notificationEvent
          .setNotificationEventType(notificationEventType.getNotificationEventTypeName());
      notificationEvent.setOperationType(operation);
      notificationEvent.setNotificationStatus(notificationStatus.getValue());
      notificationEvent.setLastFetchDate(lastRecordFetchDate);

      return notificationEvent;
    } catch (Exception e) {
      log.error("Exception while generating notification event exception:{}", e);
      throw e;
    }
  }

}
