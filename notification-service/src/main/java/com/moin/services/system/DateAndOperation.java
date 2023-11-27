package com.moin.services.system;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@MappedSuperclass
public class DateAndOperation {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
  @CreatedDate
  private Date createdAt = new Date();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSS")
  @LastModifiedDate
  private Date updatedAt;

}