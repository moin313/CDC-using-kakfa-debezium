package com.moin.services.notification.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.master.entity.NotificationEventTypeMaster;
import com.moin.services.system.DateAndOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "email_template")
public class EmailTemplate extends DateAndOperation {

  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "notification_parameters")
  private String notificationParameters;

  @ManyToOne
  @JoinColumn(name = "notification_event_type_id")
  private NotificationEventTypeMaster notificationEventTypeMaster;

  @Column(name = "user_role")
  @Enumerated(EnumType.STRING)
  private UserRole userRole;

//  @Column(name = "notification_type_id")
//  private int notificationTypeId;

  @Column(name = "subject")
  private String subject;
  
  @Column(name = "template")
  private String template;
}
