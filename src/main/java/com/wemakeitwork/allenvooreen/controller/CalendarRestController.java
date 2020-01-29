package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CalendarRestController {
    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/calendar/get/{teamid}/{startdate}/{enddate}")
    public List<Event> getCalendarViewData(@PathVariable("teamid") final Integer teamId,
                                              @PathVariable("startdate") final long startDateEpoch,
                                              @PathVariable("enddate") final long endDateEpoch) {

        // Return events that occur in the timeperiod the fullcalendar view is currently watching (from one team)
        return teamRepository.getOne(teamId).getEventList().stream()
                // Filter events that occur after the startdate (of fullcalendars view)
                .filter(x -> x.getEventStartDate().after(new Date(startDateEpoch)))
                // Filter events that occur before the enddate (of fullcalendars view)
                .filter(x -> x.getEventEndDate().before(new Date(endDateEpoch)))
                // Return the filtered events
                .collect(Collectors.toList());
    }
}