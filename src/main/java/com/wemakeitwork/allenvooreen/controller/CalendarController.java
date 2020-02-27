package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.*;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.*;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CalendarController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    EventSubscriptionRepository eventSubscriptionRepository;

    @Autowired
    TeamMembershipRepository teamMembershipRepository;

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/teamMembers")
    public ResponseEntity<Object> getTeamMembers(@PathVariable("teamid") final Integer teamId) {
        Team team  = teamRepository.getOne(teamId);
        List<TeamMembership> teamList = new ArrayList<>(team.getTeamMemberships());

        ServiceResponse<List<TeamMembership>> response = new ServiceResponse<List<TeamMembership>>("success", teamList);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamId}")
    public String showCalender(@PathVariable("teamId") final Integer teamId, Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if principal is member of team (and authorized to view calendar)
        if (teamMembershipRepository.findByTeamTeamIdAndMemberMemberId(teamId, member.getMemberId()).isPresent()) {
            Team team = teamRepository.getOne(teamId);
            httpSession.setAttribute("team", team);

            // Get and sort all teams of member.
            ArrayList<Team> sortedTeamList = teamMembershipRepository.findByMember(member)
                    .stream().map(TeamMembership::getTeam)
                    .sorted(Comparator.comparing(Team::getTeamName))
                    .collect(Collectors.toCollection(ArrayList::new));
            model.addAttribute("teamList", sortedTeamList);
            model.addAttribute("team", team);
            return "calendar";
        } else {
            model.addAttribute("statuscode", "Je hebt geen autorisatie om deze calender te bekijken omdat je geen lid bent van het team");
            return "error";
        }
    }

    @PostMapping("/calendar/new/event")
    public ResponseEntity<Object> newEvent(@RequestBody String newEventJson) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Event event = mapper.readValue(newEventJson, Event.class);
        Date startDateTime = event.getEventStartDate();
        Date endDateTime = event.getEventEndDate();

        Integer maxNumber = event.getEventMaxNumber();
        // case of periodic event
        if ((maxNumber != null) && (event.getEventId() == null)) {
            for (int i = 0; i < maxNumber; i++) {
                Date startDateTimeExtraEvent = null;
                Date endDateTimeExtraEvent = null;
                switch (event.getEventInterval()) {

                    case "day": {
                        startDateTimeExtraEvent = DateUtils.addDays(startDateTime, i);
                        endDateTimeExtraEvent = DateUtils.addDays(endDateTime, i);
                        break;
                    }
                    case "week": {
                        startDateTimeExtraEvent = DateUtils.addWeeks(startDateTime, i);
                        endDateTimeExtraEvent = DateUtils.addWeeks(endDateTime, i);
                        break;
                    }
                    case "month": {
                        startDateTimeExtraEvent = DateUtils.addMonths(startDateTime, i);
                        endDateTimeExtraEvent = DateUtils.addMonths(endDateTime, i);
                        break;
                    }
                }
                event = mapper.readValue(newEventJson, Event.class);
                if(event.getDoneByMember().getMemberId() == null){
                    event.setDoneByMember(null);
                }
                event.setEventStartDate(startDateTimeExtraEvent);
                event.setEventEndDate(endDateTimeExtraEvent);
                eventRepository.save(event);
            }
        } else {
            if(event.getDoneByMember().getMemberId() == null){
                event.setDoneByMember(null);
            }
            eventRepository.save(event);
        }

        event = eventRepository.getOne(event.getEventId());

        // this sets the activity to the medication from the activity
        if (event.getActivity() instanceof MedicationActivity){
            setActivityToMedication(event);
        }

        // removes any medication amount if the old activity was a medical one
        if (event.getEventId() != null){
            removeMedicationAmountFromActivity(event);
        }

        ServiceResponse<Event> result = new ServiceResponse<Event>("Succes", event);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PostMapping("/calendar/change/eventdate")
    public ResponseEntity<Object> changeEventDate(@RequestBody String newDatesJson) throws JsonProcessingException {
        Event newDatesEvent = mapper.readValue(newDatesJson, Event.class);

        // Get the existing event from database, and set the new dates on it
        Event existingEvent = eventRepository.getOne(newDatesEvent.getEventId());
        existingEvent.setEventStartDate(newDatesEvent.getEventStartDate());
        existingEvent.setEventEndDate(newDatesEvent.getEventEndDate());

        ServiceResponse<Event> response = new ServiceResponse<Event>("success", eventRepository.save(existingEvent));
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/event/delete")
    public ResponseEntity<Object> deleteEvent(@RequestBody String deleteEventJson) throws JsonProcessingException {

        Event eventToDelete = mapper.readValue(deleteEventJson, Event.class);

        // if the event to delete is a medical one, the medication amount needs to be balanced
        removeMedicationAmountFromActivity(eventToDelete);

        eventRepository.deleteById(eventToDelete.getEventId());
        return new ResponseEntity<Object>(eventToDelete, HttpStatus.OK);
    }


    private void setActivityToMedication(Event event){
        Optional<Medication> medication = medicationRepository.findById(((MedicationActivity) event.getActivity())
                .getMedication().getMedicationId());
        medication.ifPresent(value -> value.setTakenMedications((MedicationActivity) event.getActivity()));
        medicationRepository.save(medication.get());
    }

    private void removeMedicationAmountFromActivity(Event event){
        eventRepository.findById(event.getEventId()).stream()
                .filter(x -> x.getActivity() instanceof MedicationActivity)
                .forEach(x -> ((MedicationActivity) x.getActivity()).getMedication().upTheMedicationAmount
                        (((MedicationActivity) x.getActivity()).getTakenMedication()));
    }
}
