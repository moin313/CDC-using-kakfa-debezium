package com.moin.services.notification.event.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import com.moin.services.notification.entity.NotificationEvent;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
  public Optional<NotificationEvent> findByUserId(Long UserId);

  @Query("from NotificationEvent n where n.notificationStatus=:notificationStatus and "
      + "(n.createdAt >=:lastFetchedDateTime and n.createdAt <:currentDateTime)")
  public List<NotificationEvent> findByStatusAndDateBetween(
      @Param("lastFetchedDateTime") @Temporal(TemporalType.TIMESTAMP) Date lastFetchedDateTime,
      @Param("currentDateTime") @Temporal(TemporalType.TIMESTAMP) Date currentDateTime,
      @Param("notificationStatus") String notificationStatus);
}
