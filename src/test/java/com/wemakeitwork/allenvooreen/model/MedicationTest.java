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

    /* @Test
    // void getTakenMedications(List<MedicationActivity> testList) {
    /* void getTakenMedications() {
        //arrange and act
        testMedication.setTakenMedications(testMedicationActivity);

        //assert
        Assertions.assertThat(testMedication.getTakenMedications()).isEqualTo(testMedicationActivity);
    }

    @Test
    // void setTakenMedications(MedicationActivity medicationActivity) {
    void setTakenMedications() {
        //arrange and act
        testMedication.setTakenMedications(testMedicationActivity);

        //assert
        Assertions.assertThat(testMedication.getMedicationId()).isEqualTo(testMedicationActivity);
    } */

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

    /* public List<MedicationActivity> getTakenMedications() {
        return takenMedications;
    }

    public void setTakenMedications(MedicationActivity medicationActivity) {
        this.takenMedications.add(medicationActivity);
        this.medicationAmount -= medicationActivity.getTakenMedication();
    }

    public Integer getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(Integer medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    public String getMedicationComment() {
        return medicationComment;
    }

    public void setMedicationComment(String medicationComment) {
        this.medicationComment = medicationComment;
    }

   public void upTheMedicationAmount(Integer integer){
        this.medicationAmount += integer;
    }

    public int getMedicationRefillAmount() {
        return medicationRefillAmount;
    }

    public void upTheRefillAmount(Integer integer){
        this.medicationRefillAmount += integer;
    }

    public void setMedicationRefillAmount(int medicationRefillAmount) {
        this.medicationRefillAmount = medicationRefillAmount;

    */

}

