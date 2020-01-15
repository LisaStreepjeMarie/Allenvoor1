package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findByTeamName(String teamName);

    @Override
    void deleteById(Integer integer);
}