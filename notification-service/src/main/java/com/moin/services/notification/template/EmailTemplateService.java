package com.moin.services.notification.template;

import java.io.StringWriter;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {
  
  public String velocity(String content, Map<String, Object> model) {
    VelocityEngine engine = new VelocityEngine();
    engine.init();
    final VelocityContext context = new VelocityContext(model);
    StringWriter output = new StringWriter();
    Velocity.evaluate(context, output, "", content);
    return output.toString();
  }
}
