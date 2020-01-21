package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class JsonRestController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping(value = "/calendar/json/{team}/{startdate}/{enddate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getJsonEventListPeriod(@PathVariable("team") final Integer teamId,
                                              @PathVariable("startdate") final long startDateEpoch,
                                              @PathVariable("enddate") final long endDateEpoch){
        Team team = teamRepository.getOne(teamId);

        Date startDate = new Date(startDateEpoch);
        Date endDate = new Date(endDateEpoch);
        List<Event> eventListPeriod = new ArrayList<>();
        List<Event> fullEventList = team.getEventList();

        // Fill eventListPeriod with events in the timeperiod fullcalendar is asking for
        for (Event event : fullEventList) {
            if (event.getEventStartDate().after(startDate) && event.getEventEndDate().before(endDate)) {
                eventListPeriod.add(event);
            }
        }

        return eventListPeriod;
    }
}
