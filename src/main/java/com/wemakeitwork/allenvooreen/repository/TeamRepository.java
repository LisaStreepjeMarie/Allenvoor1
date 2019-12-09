package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
