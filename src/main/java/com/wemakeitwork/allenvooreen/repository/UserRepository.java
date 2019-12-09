package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
