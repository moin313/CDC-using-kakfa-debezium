package com.moin.services.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.moin.services.enums.EnumMaster.UserRole;
import com.moin.services.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  public List<User> findByRoleAndDeleted(UserRole status, boolean isDeleted);
  
}
