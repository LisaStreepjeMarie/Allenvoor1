package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicationActivityTest {
    MedicationActivity testMedicationActivity = new MedicationActivity();

    @Test
    void getTakenMedication() {
        //arrange
        testMedicationActivity.setTakenMedication(5);

        //assert
        Assertions.assertThat(testMedicationActivity.getTakenMedication()).isEqualTo(5);
    }

    @Test
    void getMedication() {
        //arrange
        Medication testMedication = new Medication();
        testMedicationActivity.setMedication(testMedication);

        //assert
        Assertions.assertThat(testMedicationActivity.getMedication()).isEqualTo(testMedication);
    }

    @Test
    void setMedication() {
        //arrange
        Medication testMedication = new Medication();
        testMedicationActivity.setMedication(testMedication);

        //assert
        Assertions.assertThat(testMedicationActivity.getMedication()).isEqualTo(testMedication);
    }

    @Test
    void setTakenMedication() {
        //arrange
        testMedicationActivity.setTakenMedication(5);

        //assert
        Assertions.assertThat(testMedicationActivity.getTakenMedication()).isEqualTo(5);
    }
}