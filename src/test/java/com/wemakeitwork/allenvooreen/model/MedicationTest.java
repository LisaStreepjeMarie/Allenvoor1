package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {
    Medication testMedication = new Medication();
    Team testTeam = new Team();
    List<MedicationActivity> testList = new ArrayList<>();

    @Test
    void getMedicationId() {
        //arrange
        testMedication.setMedicationId(1);

        //assert
        Assertions.assertThat(testMedication.getMedicationId()).isEqualTo(1);
    }

    @Test
    void getTakenMedications() {

    }

    @Test
    void setMedicationId() {
        //arrange
        testMedication.setMedicationId(1);

        //assert
        Assertions.assertThat(testMedication.getMedicationId()).isEqualTo(1);
    }

    @Test
    void getMedicationName() {
        //arrange
        testMedication.setMedicationName("paracetamol");

        //assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("paracetamol");
    }

    @Test
    void setMedicationName() {
        //arrange
        testMedication.setMedicationName("paracetamol");

        //assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("paracetamol");
    }

    @Test
    void getMedicationAmount() {
        //arrange
        testMedication.setMedicationAmount(10);

        //assert
        Assertions.assertThat(testMedication.getMedicationAmount()).isEqualTo(10);
    }

    @Test
    void setMedicationAmount() {
        //arrange
        testMedication.setMedicationAmount(10);

        //assert
        Assertions.assertThat(testMedication.getMedicationAmount()).isEqualTo(10);
    }

    @Test
    void getMedicationComment() {
        //arrange
        testMedication.setMedicationComment("testComment");

        //assert
        Assertions.assertThat(testMedication.getMedicationComment()).isEqualTo("testComment");
    }

    @Test
    void setMedicationComment() {
        //arrange
        testMedication.setMedicationComment("testComment");

        //assert
        Assertions.assertThat(testMedication.getMedicationComment()).isEqualTo("testComment");
    }

    @Test
    void getTeam() {
        //arrange
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }

    @Test
    void setTeam() {
        //arrange
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }

    @Test
    void removalActivityAddedAmount() {
    }
}