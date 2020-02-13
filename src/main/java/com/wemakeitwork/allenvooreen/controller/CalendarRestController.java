package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CalendarRestController {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMembershipRepository teamMembershipRepository;

    @GetMapping("/calendar/get/{teamid}/{startdate}/{enddate}")
    public ResponseEntity<Object> getEvents(@PathVariable("teamid") final Integer teamId,
                                                      @PathVariable("startdate") final long startDateEpoch,
                                                      @PathVariable("enddate") final long endDateEpoch) {

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if principal is authorized (has a teammembership; a tms) to see calendar data (or if (s)he isn't part of the team)
        Optional<TeamMembership> tms = teamMembershipRepository.findByTeamTeamIdAndMemberMemberId(teamId, member.getMemberId());
        if (tms.isPresent()) {
            // If authorized to see team's calendardata, return calendardata
            Set<Event> allEvents = teamRepository.getOne(teamId).getEventList().stream()
                    .filter(x -> x.getEventStartDate().after(new Date(startDateEpoch)))
                    .filter(x -> x.getEventEndDate().before(new Date(endDateEpoch)))
                    .collect(Collectors.toSet());
            ServiceResponse<Set<Event>> response = new ServiceResponse<Set<Event>>("success", allEvents);
            System.out.println("HAHAHAHAHA");
            return new ResponseEntity<Object>(response, HttpStatus.OK);

        } else {
            return new ResponseEntity<Object>(new Event(), HttpStatus.FORBIDDEN);
        }
    }
}