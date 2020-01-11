package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "SELECT team_id FROM event WHERE event_id = ?1", nativeQuery = true)
    Integer findTeamIdByEventId(Integer eventId);
}
