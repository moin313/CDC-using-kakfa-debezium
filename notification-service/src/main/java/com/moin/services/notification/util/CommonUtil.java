package com.moin.services.notification.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonUtil {

  private static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSSSS";
//  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

  public static String readFromFile(String filePath) {
    try {
      Path path = Paths.get(filePath);
      String date = Files.readAllLines(path).get(0);
      return date;
    } catch (Exception e) {
      log.error("readFromFile() Exception : {}", e.getMessage());
    }
    return null;
  }

  public static void writeToFile(String filePath, String data) {
    try {
      Path path = Paths.get(filePath);
      Files.write(path, data.getBytes());
    } catch (IOException e) {
      log.error("writeStringToFile() Exception : {}", e.getMessage());
    }
  }

  public static String dateToString(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    return simpleDateFormat.format(date);
  }

  public static Date stringToDate(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    try {
      return simpleDateFormat.parse(dateString);
    } catch (ParseException e) {
      log.error("stringToDate() Exception occurs for date {} exception : {}", dateString,
          e.getMessage());
      return null;
    }
  }

  public static Date getCurrentDateTime() {
    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return stringToDate(currentTime.format(formatter));
  }

//  public static boolean isValideEmail(String email) {
//    if (!StringUtils.hasLength(email)) {
//      Pattern pattern = Pattern.compile(EMAIL_REGEX);
//      Matcher matcher = pattern.matcher(email);
//      return matcher.matches();
//    }
//    return false;
//  }
}
