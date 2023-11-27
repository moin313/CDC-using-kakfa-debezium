package com.moin.services.enums;

import org.springframework.stereotype.Component;

@Component
public class EnumMaster {
  public enum NotificationEventType {
    USER_PROFILE_UPDATE(1, "User Profile Updated"), USER_PASSWORD_UPDATE(2,
        "User Password Updated"), USER_CREATE(3, "User created");

    private int notificationEventTypeId;
    private String notificationEventTypeName;

    NotificationEventType(int notificationEventTypeId, String notificationEventTypeName) {
      this.notificationEventTypeId = notificationEventTypeId;
      this.notificationEventTypeName = notificationEventTypeName;
    }

    public int getNotificationEventTypeId() {
      return this.notificationEventTypeId;
    }

    public String getNotificationEventTypeName() {
      return notificationEventTypeName;
    }

    public static NotificationEventType getById(int id) {
      for (NotificationEventType n : values()) {
        if (n.notificationEventTypeId == (id))
          return n;
      }
      return null;
    }
    public static NotificationEventType getByName(String name) {
      for (NotificationEventType n : values()) {
        if (n.notificationEventTypeName.equalsIgnoreCase(name))
          return n;
      }
      return null;
    }
  }

  public enum UserRole {
    ADMIN(1), USER(2);

    private Integer roleId;

    private UserRole(Integer roleId) {
      this.roleId = roleId;
    }

    public Integer getRoleId() {
      return this.roleId;
    }

    public static UserRole getById(int id) {
      for (UserRole n : values()) {
        if (n.roleId == (id))
          return n;
      }
      return null;
    }
  }

  public enum KafkaOperation {
    CREATE("c"), UPDATE("u"), DELETE("d");

    private String value;

    KafkaOperation(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

  }

  public enum NotificationStatus {
    PENDING("PENDING"), INPROCESS("INPROCESS"), SENT("SENT"), FAIL("FAIL");

    private String value;

    NotificationStatus(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }

  public enum NotificationType {
    EMAIL_NOTIFICATION(1);

    private int notificationType;

    private NotificationType(int notificationType) {
      this.notificationType = notificationType;
    }

    public int getnotificationType() {
      return this.notificationType;
    }

    public static NotificationType getById(int id) {
      for (NotificationType n : values()) {
        if (n.notificationType == (id))
          return n;
      }
      return null;
    }
  }


}
