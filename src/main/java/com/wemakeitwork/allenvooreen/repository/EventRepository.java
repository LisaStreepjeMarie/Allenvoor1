package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
