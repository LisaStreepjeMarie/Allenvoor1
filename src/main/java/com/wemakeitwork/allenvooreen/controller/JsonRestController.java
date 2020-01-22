package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    //TODO this doesn't work yet!
    @RequestMapping(value = "/calendar/saveEventFromPost", headers ="content-type=application/json", method = RequestMethod.POST)
    public List<Event> updatedList(@RequestBody String json) {
        System.out.println("werkt dit zo?");
        Team team = teamRepository.getOne(1);

        return team.getEventList();
    }


}
