package com.moin.services.master.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.moin.services.master.entity.NotificationEventTypeMaster;
import com.moin.services.master.entity.NotificationMappingMaster;

public interface NotificationMappingRepository
    extends JpaRepository<NotificationMappingMaster, Integer> {

  List<NotificationMappingMaster> findByNotificationEventTypeAndActive(
      NotificationEventTypeMaster notificationEventType, boolean b);


  List<NotificationMappingMaster> findByNotificationEventType(
      NotificationEventTypeMaster notificationEventType);
}
