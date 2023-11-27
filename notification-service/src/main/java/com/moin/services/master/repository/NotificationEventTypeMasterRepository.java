package com.moin.services.master.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.moin.services.master.entity.NotificationEventTypeMaster;

public interface NotificationEventTypeMasterRepository
    extends CrudRepository<NotificationEventTypeMaster, Integer> {

  NotificationEventTypeMaster findByEventType(String notificationEventType);

  
  NotificationEventTypeMaster findByEventTypeAndIsActive(String notificationEventType,
      boolean isActive);

//  NotificationEventTypeMaster findByIdAndIsActive(int notificationEventTypeId,
//      boolean isActive);

  public List<NotificationEventTypeMaster> findByIsActive(boolean isActive);

}
