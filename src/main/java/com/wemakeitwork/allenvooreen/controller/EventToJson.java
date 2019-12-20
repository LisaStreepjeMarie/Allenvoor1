package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;

public class EventToJson {

    public static String eventToJson(Event event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // create a post
        event.setEventId(99);
        event.setEventName("event naam");
        Activity activity = new Activity();
        event.setActivity(activity);
        event.setEventComment("Commentaar");

        // Convert object to JSON string
        String postJson = mapper.writeValueAsString(event);
        System.out.println(postJson);
        return postJson;
    }
}
