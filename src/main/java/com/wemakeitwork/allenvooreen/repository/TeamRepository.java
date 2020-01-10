package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findByTeamName(String teamName);

    @Override
    void deleteById(Integer integer);

    @Query(value = "SELECT team_name_team_id FROM members_team_name WHERE member_member_id = ?1", nativeQuery = true)
    Integer findTeamIdByMemberid(Integer member_id);

    @Query(value = "SELECT * FROM team WHERE team_id = ?1", nativeQuery = true)
    Team findTeamById(Integer team_id);

    // @Query(value = "SELECT team_name_team_id FROM members_team_name WHERE member_member_id = ?1", nativeQuery = true)
    @Query(value = "SELECT team_team_id FROM team_membername WHERE membername_member_id = ?1", nativeQuery = true)
     List<Integer> findTeamsByIdMember(Integer MemberId);

}