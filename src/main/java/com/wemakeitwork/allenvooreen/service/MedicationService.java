package com.wemakeitwork.allenvooreen.service;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationService implements MedicationServiceInterface {

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public void save(Medication medication) {
        medicationRepository.save(medication);
    }

    @Override
    public Optional<Medication> findById(Integer integer) {
        return medicationRepository.findById(integer);
    }

    @Override
    public Optional<Medication> findByMedicationName(String medicationName) {
        return medicationRepository.findByMedicationName(medicationName);
    }
}
