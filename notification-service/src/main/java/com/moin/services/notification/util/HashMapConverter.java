package com.moin.services.notification.util;

import java.io.IOException;
import java.util.Map;
import javax.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

    String usedParametersJson = null;
    try {
      usedParametersJson = objectMapper.writeValueAsString(customerInfo);
    } catch (final JsonProcessingException e) {
      log.error("JSON writing error", e);
    }

    return usedParametersJson;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

    Map<String, Object> usedParametersJson = null;
    try {
      usedParametersJson = objectMapper.readValue(customerInfoJSON, Map.class);
    } catch (final IOException e) {
      log.error("JSON reading error", e);
    }

    return usedParametersJson;
  }

}
