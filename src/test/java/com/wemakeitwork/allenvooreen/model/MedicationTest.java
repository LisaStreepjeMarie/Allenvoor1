package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MedicationTest {

    Medication testMedication = new Medication();
    Team testTeam = new Team();
    MedicationActivity testMedicationActivity = new MedicationActivity();

    @Test
    void getMedicationId() {
        //arrange and act
        testMedication.setMedicationId(2);

        //assert
        Assertions.assertThat(testMedication.getMedicationId()).isEqualTo(2);
    }

    @Test
    void setMedicationId() {
        //arrange and act
        testMedication.setMedicationId(2);

        //assert
        Assertions.assertThat(testMedication.getMedicationId()).isEqualTo(2);
    }

    @Test
    void getMedicationName() {
        //arrange and act
        testMedication.setMedicationName("test");

        //assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("test");
    }

    @Test
    void setMedicationName() {
        //arrange and act
        testMedication.setMedicationName("test");

        //assert
        Assertions.assertThat(testMedication.getMedicationName()).isEqualTo("test");
    }

    @Test
    void getBought() {
        //arrange and act
        testMedication.setBought(true);

        //assert
        Assertions.assertThat(testMedication.getBought()).isEqualTo(true);
    }

    @Test
    void setBought() {
        //arrange and act
        testMedication.setBought(true);

        //assert
        Assertions.assertThat(testMedication.getBought()).isEqualTo(true);
    }

    /* public void toggleBought() {
        this.bought = !this.bought;
    } */

    @Test
    void getMedicationAmount() {
        //arrange and act
        testMedication.setMedicationAmount(15);

        //assert
        Assertions.assertThat(testMedication.getMedicationAmount()).isEqualTo(15);
    }

    @Test
    void setMedicationAmount() {
        //arrange and act
        testMedication.setMedicationAmount(15);

        //assert
        Assertions.assertThat(testMedication.getMedicationAmount()).isEqualTo(15);
    }

    @Test
    void getMedicationComment() {
        //arrange and act
        testMedication.setMedicationComment("test");

        //assert
        Assertions.assertThat(testMedication.getMedicationComment()).isEqualTo("test");
    }

    @Test
    void setMedicationComment() {
        //arrange and act
        testMedication.setMedicationComment("test2");

        //assert
        Assertions.assertThat(testMedication.getMedicationComment()).isEqualTo("test2");
    }

    /* public void setTakenMedications(MedicationActivity medicationActivity) {
        this.takenMedications.add(medicationActivity);
        this.medicationAmount -= medicationActivity.getTakenMedication();
    } */

    @Test
    void getTakenMedications() {
        //arrange
        int takenMedications = 2;
        int medicationAmount = 20;

        //act
        medicationAmount -= takenMedications;

        //assert
        Assertions.assertThat(medicationAmount).isEqualTo(18);
    }

    @Test
    void setTakenMedications() {
        //arrange
        int takenMedications = 2;
        int medicationAmount = 20;

        //act
        medicationAmount -= takenMedications;

        //assert
        Assertions.assertThat(medicationAmount).isEqualTo(18);
    }

    @Test
    void upTheMedicationAmount(){
        //arrange
        int integer = 25;
        int medicationAmount = 10;

        //act
        medicationAmount += integer;

        //assert
        Assertions.assertThat(medicationAmount).isEqualTo(35);
    }

    @Test
    void getMedicationRefillAmount() {
        //arrange and act
        testMedication.setMedicationRefillAmount(20);

        //assert
        Assertions.assertThat(testMedication.getMedicationRefillAmount()).isEqualTo(20);
    }

    @Test
    void upTheRefillAmount(){
        //arrange
        int integer = 5;
        int medicationRefillAmount = 20;

        //act
        medicationRefillAmount += integer;

        //assert
        Assertions.assertThat(medicationRefillAmount).isEqualTo(25);
    }

    @Test
    void setMedicationRefillAmount() {
        //arrange and act
        testMedication.setMedicationRefillAmount(30);

        //assert
        Assertions.assertThat(testMedication.getMedicationRefillAmount()).isEqualTo(30);
    }

    @Test
    void getMedication() {
        //arrange and act
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }

    @Test
    void setMedication() {
        //arrange and act
        testMedication.setTeam(testTeam);

        //assert
        Assertions.assertThat(testMedication.getTeam()).isEqualTo(testTeam);
    }

}

