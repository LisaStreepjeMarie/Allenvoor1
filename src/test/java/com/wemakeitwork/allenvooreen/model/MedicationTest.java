package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {
    Medication testMedication = new Medication();

    @Test
    void getMedicationName() {

        //arrange
        testMedication.setMedicationName("testmedicatie");

        //assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("testmedicatie");
    }

    @Test
    void setMedicationName(){

        //arrange
        testMedication.setMedicationName("testmedicatie");

        // assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("testmedicatie");
    }

    @Test
    void getTeam(){

        //
        Team testTeam = new Team();
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }

    @Test
    void setTeam(){

        //
        Team testTeam = new Team();
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }
}