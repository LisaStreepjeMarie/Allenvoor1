package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
