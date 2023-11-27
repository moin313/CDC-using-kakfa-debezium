package com.moin.services.notification.entity;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moin.services.enums.EnumMaster.NotificationEventType;
import com.moin.services.enums.EnumMaster.NotificationStatus;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.notification.event.repository.NotificationEventRepository;
import com.moin.services.notification.template.EmailTemplate;
import com.moin.services.notification.template.EmailTemplateRepository;
import com.moin.services.notification.template.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

  @Autowired
  private EmailTemplateRepository emailTemplateRepository;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private NotificationEventRepository notificationEventRepository;

  public void sendMail(Mail mail, Map<String, Object> model, NotificationEvent notificationEvent) {
    Optional<EmailTemplate> notificationTemplateOpt =
        emailTemplateRepository.findByNotificationEventTypeMaster_EventTypeAndUserRole(
            NotificationEventType.getByName(mail.getNotificationEventType()).name(),
            UserRole.valueOf(model.get("role").toString()));
    if (!notificationTemplateOpt.isPresent()) {
      log.info("email template not found for eventType id:{}", mail.getNotificationEventType());
      return;
    }
    EmailTemplate emailTemplate = notificationTemplateOpt.get();
    mail.setSubject(emailTemplateService.velocity(emailTemplate.getSubject(), model));
    String body = emailTemplateService.velocity(emailTemplate.getTemplate(), model);
    mail.setBody(body);
    try {
      send(mail);
    } catch (Exception e) {
      e.printStackTrace();
    }
    notificationEvent.setNotificationStatus(NotificationStatus.SENT.getValue());
    notificationEventRepository.save(notificationEvent);
  }

  private void send(Mail mail) throws Exception {
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.port", "587");
    props.setProperty("mail.smtp.socketFactory.port", "587");
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.auth", "true");

    Authenticator auth = new MyAuthenticator();
    Session smtpSession = Session.getInstance(props, auth);
    smtpSession.setDebug(false);
    MimeMessage message = new MimeMessage(smtpSession);
    try {
      message.setFrom(new InternetAddress(mail.getFrom()));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));

      final String encoding = "text/html";
      message.setSubject(mail.getSubject(), encoding);
      message.setContent(mail.getBody(), encoding);
      Transport.send(message);
      log.info("Email Sent to: ", mail.getTo());
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  static class MyAuthenticator extends Authenticator {
    public MyAuthenticator() {
      super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
      String username = "aqmoin313@gmail.com";
      String password = "wblw fqls buca fxgg";
      if ((username != null) && (username.length() > 0) && (password != null)
          && (password.length() > 0)) {

        return new PasswordAuthentication(username, password);
      }
      return null;
    }
  }


}
