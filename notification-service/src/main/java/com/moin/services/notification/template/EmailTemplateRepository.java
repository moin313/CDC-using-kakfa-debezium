package com.moin.services.notification.template;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moin.services.enums.EnumMaster.UserRole;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
  public Optional<EmailTemplate> findByNotificationEventTypeMaster_EventTypeAndUserRole(
      String notifiactionEventTyeId, UserRole userRole);
}
