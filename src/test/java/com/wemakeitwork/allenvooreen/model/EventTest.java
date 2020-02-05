package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Date;

class EventTest {
    Event testEvent = new Event();
    //Activity testActivity = new Activity();


    @Test
    void getEventName() {
        //arrange
        testEvent.setEventName("test");

        //assert
        Assertions.assertThat(testEvent.getEventName()).isEqualTo("test");
    }

    @Test
    void setEventName() {
        //arrange
        testEvent.setEventName("test");

        //assert
        Assertions.assertThat(testEvent.getEventName()).isEqualTo("test");
    }

    @Test
    void getEventId() {
        //arrange
        testEvent.setEventId(2);

        //assert
        Assertions.assertThat(testEvent.getEventId()).isEqualTo(2);
    }

    @Test
    void setEventId() {
        //arrange
        testEvent.setEventId(2);

        //assert
        Assertions.assertThat(testEvent.getEventId()).isEqualTo(2);
    }

/*
    @Test
    void getActivity() {
        //arrange
        testEvent.setActivity(testActivity);

        //assert
        Assertions.assertThat(testEvent.getActivity()).isEqualTo(testActivity);
    }

    @Test
    void setActivity() {
        //arrange
        testEvent.setActivity(testActivity);

        //assert
        Assertions.assertThat(testEvent.getActivity()).isEqualTo(testActivity);
    }
*/

    @Test
    void getEventDate() {
        //arrange
        DateTime testdate = new DateTime(2323223232L);
        testEvent.setEventStartDate(testdate);

        //assert
        Assertions.assertThat(testEvent.getEventStartDate()).isEqualTo(testdate);
    }

    @Test
    void setEventDate() {
        //arrange
        DateTime testdate = new DateTime(2323223232L);
        testEvent.setEventStartDate(testdate);

        //assert
        Assertions.assertThat(testEvent.getEventStartDate()).isEqualTo(testdate);
    }

    @Test
    void getEventComment() {
        //arrange
        testEvent.setEventComment("hallo");

        //assert
        Assertions.assertThat(testEvent.getEventComment()).isEqualTo("hallo");
    }

    @Test
    void setEventComment() {
        //arrange
        testEvent.setEventComment("hallo");

        //assert
        Assertions.assertThat(testEvent.getEventComment()).isEqualTo("hallo");
    }
}