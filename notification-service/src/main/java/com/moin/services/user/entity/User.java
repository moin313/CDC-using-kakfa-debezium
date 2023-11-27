package com.moin.services.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.system.DateAndOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends DateAndOperation {
  
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "username")
  private String userName;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(name = "is_deleted")
  private Boolean deleted;

  public User(long id) {
    this.id = id;
  }

}
