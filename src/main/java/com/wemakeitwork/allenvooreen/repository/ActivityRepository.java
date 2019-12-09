package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
