package com.moin.services.schedular;

import java.util.Date;
import java.util.List;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.moin.services.enums.EnumMaster.NotificationStatus;
import com.moin.services.notification.entity.NotificationEvent;
import com.moin.services.notification.event.repository.NotificationEventRepository;
import com.moin.services.notification.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class NotificationEventScheduler {

  @Autowired
  private AmqpTemplate rabbitTemplate;

  @Autowired
  private NotificationEventRepository notificationEventRepository;

  @Value("${rabbitmq.exchange.notification}")
  private String notifictionExchange;

  @Value("${rabbitmq.routing.key.user.notification}")
  private String userNotifiationRoutingKey;

  @Value("${scheduler.date.processing.file}")
  private String cronJobDateFilePath;

  @Scheduled(fixedDelayString = "${notification.event.schelduler.fixeddelay}",
      initialDelayString = "${notification.event.schelduler.initialdelay}")
  public void notificationSendSchedular() {

    log.info("=================notificationSendSchedular started=================");

    String lastFetchedDate = CommonUtil.readFromFile(cronJobDateFilePath);
    Date lastFetchedDateTime = CommonUtil.stringToDate(lastFetchedDate);
    Date currentDateTime = CommonUtil.getCurrentDateTime();
    String currentDateTimeString = CommonUtil.dateToString(currentDateTime);
    log.info("lastFetchedDate:{}, currentDateTimeString:{}", lastFetchedDate,
        currentDateTimeString);
    CommonUtil.writeToFile(cronJobDateFilePath, currentDateTimeString);

    List<NotificationEvent> notificationList =
        notificationEventRepository.findByStatusAndDateBetween(lastFetchedDateTime, currentDateTime,
            NotificationStatus.PENDING.getValue());

    log.info("Sending {} Notifications.", notificationList.size());
    for (NotificationEvent notification : notificationList) {
      try {
        rabbitTemplate.convertAndSend(notifictionExchange, userNotifiationRoutingKey,
            notification.getId());
        notification.setNotificationStatus(NotificationStatus.INPROCESS.getValue());
        notificationEventRepository.save(notification);
      } catch (Exception e) {
        log.error("Exception occurred while sending message to rabbitmq for id :{}, {}",
            notification.getId(), e.getMessage());
      }
    }
    log.info("=================notificationSendSchedular end=================");
  }

}
