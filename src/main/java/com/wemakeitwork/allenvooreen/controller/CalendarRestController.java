package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getEvents(@PathVariable("teamid") final Integer teamId,
                                                      @PathVariable("startdate") final long startDateEpoch,
                                                      @PathVariable("enddate") final long endDateEpoch) {

        List<Event> allEvents =  teamRepository.getOne(teamId).getEventList().stream()
                .filter(x -> x.getEventStartDate().after(new Date(startDateEpoch)))
                .filter(x -> x.getEventEndDate().before(new Date(endDateEpoch)))
                //joda-time
                /*.filter(x -> x.getEventStartDate().isAfter(new DateTime(startDateEpoch)))
                .filter(x -> x.getEventEndDate().isBefore(new DateTime(endDateEpoch))) */
                .collect(Collectors.toList());

        /*for (Event event : allEvents) {
            System.out.println("gebonden activiteit" + event.getActivity().getActivityName());
        }*/

        ServiceResponse<List<Event>> response = new ServiceResponse<List<Event>>("success", allEvents);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}