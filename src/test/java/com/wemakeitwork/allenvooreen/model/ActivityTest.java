package com.wemakeitwork.allenvooreen.model;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActivityTest {

    public Activity testActivity = new Activity();
    public Activity testActivityB = new Activity();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;

    @Test
    void testGetActivityId() {
        //arrange
        testActivity.setActivityId(1);
        testActivityB.setActivityId(2);

        //activate + assert
        assertEquals(testActivity.getActivityId(), 1);
        assertEquals(testActivityB.getActivityId(), 1);
    }


    @Test
    void setActivityId() {
    }

    @Test
    void getActivityName() {
        //arrange
        testActivity.setActivityName("schoonmaken");
        testActivityB.setActivityName("wandelen");

        //activate + assert
        assertEquals(testActivity.getActivityName(), "schoonmaken");
        assertEquals(testActivityB.getActivityName(), "hond uitlaten");
    }

    @Test
    void setActivityName() {
    }

    @Test
    void getActivityCategory() {
    }

    @Test
    void setActivityCategory() {
    }


}
