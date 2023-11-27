package com.moin.services.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.moin.services.system.DateAndOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "notification_event_type_master")
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NotificationEventTypeMaster extends DateAndOperation {

  @Id
  @Column(name = "id")
  private long id;
  
  @Column(name = "event_type")
  private String eventType;

  @Column(name = "is_active", columnDefinition = "bit default 1", nullable = false)
  private Boolean isActive = true;

  public NotificationEventTypeMaster(long id) {
    this.id = id;
  }
  
  

}
