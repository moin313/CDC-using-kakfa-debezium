package com.moin.services.notification.entity;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.moin.services.notification.util.HashMapConverter;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "notification_event")
public class NotificationEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private long id;

  @Column(name = "role_id")
  private int roleId;

  @Column(name = "role_name")
  private String roleName;

  @Column(name = "notification_event_type_id")
  private int notificationEventTypeId;

  @Column(name = "notification_event_type")
  private String notificationEventType;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "sender_email")
  private String senderEmail; 

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
  @CreatedDate
  private Date createdAt = new Date();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "last_fetch_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
  private Date lastFetchDate;

  @Column(name = "notification_status")
  private String notificationStatus;

  @Convert(converter = HashMapConverter.class)
  @Column(name = "used_parameters_json")
  private Map<String, Object> usedParametersJson;

  @Column(name = "operation_type")
  private String operationType;
}
