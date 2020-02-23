package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.EventSubscription;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.EventSubscriptionRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
public class EventController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMembershipRepository teamMembershipRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    //TODO not sure if the mapping works correctly here
    protected void newEventWithMedicationActivity(Event event) {
        //tried out a stream to get the correct medication and set medication activity
        medicationRepository.findAll().stream()
                .filter(x -> {
                    assert ((MedicationActivity) event.getActivity()).getMedication() != null;
                    return x.getMedicationId() == ((MedicationActivity) event.getActivity()).getMedication().getMedicationId();
                })
                .forEach(x -> x.setTakenMedications((MedicationActivity) event.getActivity()));
        event.getTeam().getTeamId();
    }

    // Allow empty string in datefield (i.e. write them as NULL to database)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/event/new")
    protected String newEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity")
            MedicationActivity medicationActivity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam((Team) httpSession.getAttribute("team"));
            event.getActivity().setActivityName(event.getEventName());

            if (event.getActivity() instanceof MedicationActivity){
                if (medicationActivity.getMedication() == null) {
                    return "redirect:/calendar/" + team.getTeamId();
                } else {
                    event.setActivity(medicationActivity);
                    newEventWithMedicationActivity(event);
                }
            }

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @GetMapping("/event/{eventid}/subscriptionlist")
    protected String showSubscriptionListPage(@PathVariable("eventid") final Integer eventId, Model model) {
        model.addAttribute("eventId", eventId);
        return "subscriptionList";
    }

    @GetMapping("/event/{eventid}/getsubscriptionlist")
    public ResponseEntity<Object> fillSubscriptionList(@PathVariable("eventid") final Integer eventId) throws InvalidPropertyException {
        // Get logged in user
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get event from eventId
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "Event", "Dit event bestaat niet"));

        // Check if logged in user is authorized to view subscriptionlist of event
        if ( teamMembershipRepository.findByTeamAndMember(event.getTeam(), member).isEmpty() ) {
            ServiceResponse<String> response = new ServiceResponse<>("Foutmelding", "Je bent niet bevoegd de intekenlijst te bezichtigen");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Show modal with generic error message if an exception is thrown.
        Set<EventSubscription> eventSubscriptionList;
        try {
            eventSubscriptionList = eventSubscriptionRepository.findByEventEventId(eventId);
        } catch (Exception e) {
            ServiceResponse<String> response = new ServiceResponse<>("Foutmelding", "De intekenlijst kon niet worden geladen");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ServiceResponse<Set<EventSubscription>> response = new ServiceResponse<>("succes", eventSubscriptionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/event/subscribe")
    public ResponseEntity<Object> subscribeToEvent(@RequestBody String subscribeEvent) throws JsonProcessingException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventSubscription eventSubscription = mapper.readValue(subscribeEvent, EventSubscription.class);

        eventSubscription.setMember(member);
        eventSubscriptionRepository.save(eventSubscription);

        ServiceResponse<EventSubscription> response = new ServiceResponse<EventSubscription>("success", eventSubscription);
        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

    @PostMapping("/event/unsubscribe")
    public ResponseEntity<Object> unsubscribeFromEvent(@RequestBody String unsubscribeFromEvent) throws JsonProcessingException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Convert eventSubscription json to object, lookup event that it belongs to, and set that event to the eventSubscription.
        EventSubscription eventSubscriptionToDelete = mapper.readValue(unsubscribeFromEvent, EventSubscription.class);
        eventSubscriptionToDelete.setEvent(eventSubscriptionRepository
                .findById(eventSubscriptionToDelete.getSubscriptionId())
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "eventSubscriptionToDelete", "Deze inschrijving bestaat niet"))
                .getEvent());
        eventSubscriptionRepository.delete(eventSubscriptionToDelete);

        ServiceResponse<EventSubscription> response = new ServiceResponse<EventSubscription>("success", eventSubscriptionToDelete);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
