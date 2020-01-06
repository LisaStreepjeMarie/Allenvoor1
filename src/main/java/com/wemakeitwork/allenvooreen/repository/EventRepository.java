package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "SELECT activity_id FROM event WHERE event.event_id = ?1", nativeQuery = true)
    Integer findActivityIdByEventId(Integer event_id);
}
