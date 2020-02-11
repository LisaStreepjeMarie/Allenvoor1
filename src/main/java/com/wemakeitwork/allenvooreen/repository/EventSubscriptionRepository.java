package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.EventSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EventSubscriptionRepository extends JpaRepository<EventSubscription, Integer> {

    Set<EventSubscription> findByEventEventId(Integer eventId);
}
