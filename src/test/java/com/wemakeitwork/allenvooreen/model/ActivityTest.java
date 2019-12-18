package com.wemakeitwork.allenvooreen.model;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActivityTest {
    private Activity testActivity = new Activity();

    @Test
    void testGetActivityId() {
        //arrange
        testActivity.setActivityId(1);

        //assert
        Assertions.assertThat(testActivity.getActivityId()).isEqualTo(1);
    }


    @Test
    void setActivityId() {
        //arrange
        testActivity.setActivityId(1);

        //assert
        Assertions.assertThat(testActivity.getActivityId()).isEqualTo(1);
    }

    @Test
    void getActivityName() {
        //arrange
        testActivity.setActivityName("wandelen");

        //assert
        Assertions.assertThat(testActivity.getActivityName()).isEqualTo("wandelen");
    }

    @Test
    void setActivityName() {
        //arrange
        testActivity.setActivityName("wandelen");

        //assert
        Assertions.assertThat(testActivity.getActivityName()).isEqualTo("wandelen");
    }

    @Test
    void getActivityCategory() {
        //arrange
        testActivity.setActivityCategory("Medisch");

        //assert
        Assertions.assertThat(testActivity.getActivityCategory()).isEqualTo("Medisch");
    }

    @Test
    void setActivityCategory() {
        //arrange
        testActivity.setActivityCategory("Medisch");

        //assert
        Assertions.assertThat(testActivity.getActivityCategory()).isEqualTo("Medisch");
    }

    @AfterEach
    void cleanUp(){
        testActivity = null;
    }

}
