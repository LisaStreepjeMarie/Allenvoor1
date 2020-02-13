package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class EventTest {
    Event testEvent = new Event();
    Date dateNow = new Date(System.currentTimeMillis());

    @Test
    void getAndSetEventName() {
        //arrange
        testEvent.setEventName("test");

        //assert
        Assertions.assertThat(testEvent.getEventName()).isEqualTo("test");
    }

    @Test
    void getAndSetEventId() {
        //arrange
        testEvent.setEventId(2);

        //assert
        Assertions.assertThat(testEvent.getEventId()).isEqualTo(2);
    }

    @Test
    void getAndSetEventInterval() {
        //arrange
        testEvent.setEventInterval("day");

        //assert
        Assertions.assertThat(testEvent.getEventInterval()).isEqualTo("day");
    }

    @Test
    void getAndSetEventMaxNumber() {
        //arrange
        testEvent.setEventMaxNumber(5);

        //assert
        Assertions.assertThat(testEvent.getEventMaxNumber()).isEqualTo(5);
    }

    @Test
    void getAndSetActivity() {
        //arrange
        Activity activity = new MedicationActivity();
        testEvent.setActivity(activity);

        //assert
        Assertions.assertThat(testEvent.getActivity()).isEqualTo(activity);
    }

    @Test
    void getAndSetEventDate() {
        //arrange
        testEvent.setEventStartDate(dateNow);

        //assert
        Assertions.assertThat(testEvent.getEventStartDate()).isEqualTo(dateNow);
    }

    @Test
    void getAndSetEventComment() {
        //arrange
        testEvent.setEventComment("hallo");

        //assert
        Assertions.assertThat(testEvent.getEventComment()).isEqualTo("hallo");
    }

    @Test
    void getAndSetEventDoneDate() {
        //arrange
        testEvent.setEventDoneDate(dateNow);

        //assert
        Assertions.assertThat(testEvent.getEventDoneDate().equals(dateNow));
    }

    @Test
    void getEventEndDate() {
        //arrange
        testEvent.setEventEndDate(dateNow);

        //assert
        Assertions.assertThat(testEvent.getEventEndDate().equals(dateNow));
    }

    @Test
    void getAndSetTeam() {
        //arrange
        Team team = new Team();
        testEvent.setTeam(team);

        //assert
        testEvent.getTeam().equals(team);
    }

    @Test
    void getDoneByMember() {
        //arrange
        Member member = new Member();
        testEvent.setDoneByMember(member);

        testEvent.getDoneByMember().equals(member);
    }
}