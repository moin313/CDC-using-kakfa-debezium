package com.moin.services.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.system.DateAndOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notification_mapping_master")
@Data
@EqualsAndHashCode(callSuper=false)
public class NotificationMappingMaster extends DateAndOperation {

  @Id
  @Column(name = "id")
  private long id;

  @ManyToOne
  @JoinColumn()//name = "notification_event_type_id")
  private NotificationEventTypeMaster notificationEventType;

//  @Column(name = "notification_type_id")
//  private int notificationTypeId; pull push

  @Column(name = "is_active", columnDefinition = "bit default 1")
  private boolean active = true;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

}
