package com.moin.services.notification.event.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.moin.services.enums.EnumMaster.KafkaOperation;
import com.moin.services.enums.EnumMaster.NotificationEventType;
import com.moin.services.enums.EnumMaster.NotificationStatus;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.master.entity.NotificationEventTypeMaster;
import com.moin.services.master.entity.NotificationMappingMaster;
import com.moin.services.master.repository.NotificationEventTypeMasterRepository;
import com.moin.services.master.repository.NotificationMappingRepository;
import com.moin.services.notification.entity.NotificationEvent;
import com.moin.services.notification.event.NotificationEventService;
import com.moin.services.notification.event.repository.NotificationEventRepository;
import com.moin.services.notification.handler.NotificationEventHandler;
import com.moin.services.user.entity.User;
import com.moin.services.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserNotificationEvent implements NotificationEventService {

  @Autowired
  private NotificationEventHandler notificationEventHandler;

  @Autowired
  private NotificationEventTypeMasterRepository eventTypeMasterRepository;

  @Autowired
  private NotificationMappingRepository notificationMappingRepository;

  @Autowired
  private NotificationEventRepository notificationEventRepository;

  @Autowired
  private UserRepository userRepository;

  @Value("${sender.email}")
  private String senderEmail;

  @Override
  public void generateNotificationEvent(JsonObject payload) {
    JsonObject userJsonAfter = payload.get("after").getAsJsonObject();
    Long userId = userJsonAfter.get("id").getAsLong();

    log.info("generateUserNotificationEvent() entry for  userId:{} ", userId);

    getNotificationDetails(userId, userJsonAfter, NotificationEventType.USER_CREATE,
        KafkaOperation.CREATE.toString());
  }

  private void getNotificationDetails(long userId, JsonObject userJsonAfter,
      NotificationEventType notificationEventType, String kafkaOpration) {
    List<NotificationMappingMaster> notificationMappingMasterList = new ArrayList<>();
    try {
      NotificationEventTypeMaster EventType =
          eventTypeMasterRepository.findByEventType(notificationEventType.name());
      notificationMappingMasterList =
          notificationMappingRepository.findByNotificationEventType(EventType);
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error("generateNotificationEvent() Exception user creation with id : {} | exception : {}",
          userId, ex.getMessage());
    }
    for (NotificationMappingMaster notificationMappingMaster : notificationMappingMasterList) {
      generateNotificationByUserType(notificationMappingMaster.getRole().getRoleId(),
          notificationMappingMaster, notificationEventType, userId, kafkaOpration);
    }
  }

  private void generateNotificationByUserType(Integer roleId,
      NotificationMappingMaster notificationMappingMaster,
      NotificationEventType notificationEventType, Long userId, String kafkaOpration) {
    NotificationEvent notificationEvent = null;
    Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
      log.error("User Not present with id : {}", userId);
      return;
    }
    User user = userOpt.get();
    try {
      UserRole userRole = UserRole.getById(roleId);
      log.info("CURRENT ROLE : {}", userRole);
      switch (userRole) {
        case USER:
          if (user.getRole().equals(userRole.USER)) {
            notificationEvent = new NotificationEvent();
            notificationEvent =
                notificationEventHandler.generateNotificationEvent(user, NotificationStatus.PENDING,
                    notificationEventType, user.getCreatedAt(), KafkaOperation.CREATE.toString());
            saveNotificationByNotificationType(notificationEvent, user);
          }
          break;
        case ADMIN:
          List<User> userList = userRepository.findByRoleAndDeleted(UserRole.ADMIN, false);
          log.info("The count of present admin is : {}", userList.size());
          for (User adminUser : userList) {
            notificationEvent = new NotificationEvent();
            notificationEvent = notificationEventHandler.generateNotificationEvent(adminUser,
                NotificationStatus.PENDING, notificationEventType, adminUser.getCreatedAt(),
                KafkaOperation.CREATE.toString());
            saveNotificationByNotificationType(notificationEvent, adminUser);
          }
      }

    } catch (Exception e) {
      log.error("generateNotificationByUserType() Excpetion occured for user id : {} is : {}",
          userId, e.getMessage());
    }
  }

  private void saveNotificationByNotificationType(NotificationEvent notificationEvent, User user) {
    Map<String, Object> data = new HashMap<>();

    String toEmail = user.getEmail();
    notificationEvent.setEmail(toEmail);
    notificationEvent.setSenderEmail(senderEmail);
    data = setEmailReplaceParameters(data, user);
    notificationEvent.setUsedParametersJson(data);
    log.info("Notification prepared for Email : {}", toEmail);
    notificationEventRepository.save(notificationEvent);
  }

  private Map<String, Object> setEmailReplaceParameters(Map<String, Object> data, User user) {
    data.put("userName", user.getUserName());
    data.put("email", user.getEmail());
    data.put("role", user.getRole().name());
    return data;
  }
}

