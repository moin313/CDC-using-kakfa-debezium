package com.moin.services.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.moin.services.notification.entity.EmailService;
import com.moin.services.notification.entity.Mail;
import com.moin.services.notification.entity.NotificationEvent;
import com.moin.services.notification.event.repository.NotificationEventRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQConsumer {

  @Value("${rabbitmq.queue.user.notification}")
  private String userNotificationQueue;

  @Autowired
  private NotificationEventRepository notificationEventRepository;

  @Autowired
  private EmailService emailService;

  @RabbitListener(queues = "${rabbitmq.queue.user.notification}", concurrency = "1",
      exclusive = true)
  public void userNotificationConsumer(long notificationEventId) throws Exception {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    executorService.submit(() -> {
      try {
        prepareAndSendEmail(notificationEventId);
      } catch (Exception e) {
        log.error("userNotificationConsumer() Exception : {}",e.getMessage());
        return;
      }
    });
    executorService.shutdown();
  }

  private void prepareAndSendEmail(long notificationEventId) {
    Optional<NotificationEvent> notificationEventOpt =
        notificationEventRepository.findById(notificationEventId);
    if (!notificationEventOpt.isPresent()) {
      log.info("No Data Present for notification event id : {}", notificationEventId);
      return;
    }
    NotificationEvent notificationEvent = notificationEventOpt.get();
    log.info("Preparing email for id: {}", notificationEvent.getEmail());
    Mail mail = new Mail();
    mail.setTo(notificationEvent.getEmail());
    mail.setFrom(notificationEvent.getSenderEmail());
    mail.setNotificationEventType(notificationEvent.getNotificationEventType());
    Map<String, Object> model = getModel(notificationEvent);
    emailService.sendMail(mail, model, notificationEvent);
  }

  private Map<String, Object> getModel(NotificationEvent notificationEvent) {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", notificationEvent.getUsername());
    model.put("role", notificationEvent.getRoleName());
    model.put("email", notificationEvent.getEmail());
    model.put("AppOwner", "${app.owner.name}");
    model.put("subject", notificationEvent.getNotificationEventType());
    return model;
  }

}
