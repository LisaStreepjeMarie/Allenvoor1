package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    private Activity testActivity = new LeisureActivity();

    private Event event = new Event();

    @Test
    void getAndSetActivityId() {
        //arrange
        testActivity.setActivityId(1);

        //assert
        Assertions.assertThat(testActivity.getActivityId()).isEqualTo(1);
    }

    @Test
    void getAndSetActivityName() {
        //arrange
        testActivity.setActivityName("wandelen");

        //assert
        Assertions.assertThat(testActivity.getActivityName()).isEqualTo("wandelen");
    }

    @Test
    void getAndSetActivityEvent() {
        //arrange
        event.setEventName("TestEventNaam");
        testActivity.setEvent(event);

        Assertions.assertThat(testActivity.getEvent().getEventName()).isEqualTo("TestEventNaam");
    }

    @AfterEach
    void cleanUp(){
        testActivity = null;
    }

}
