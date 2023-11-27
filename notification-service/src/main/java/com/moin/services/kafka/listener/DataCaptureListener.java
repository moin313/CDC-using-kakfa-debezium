package com.moin.services.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.moin.services.kafka.KafkaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataCaptureListener {

  @Autowired
  KafkaUtil kafkaUtil;

  @KafkaListener(topics = "${kafka.topic.user}")
  public void kafkaConsumerForClientInvoice(ConsumerRecord<String, String> record) {
    log.info("=======DataCaptureListener started for topic=kafka.topic.user=======");
    String recordValue = record.value();
    if (null == recordValue || recordValue.isEmpty()) {
      log.info("value not received in payload.");
      return;
    }
    kafkaUtil.getPayloadAndPerformCreateOperationEvents(recordValue);
    log.info("User topic executed successfully");
  }
}
