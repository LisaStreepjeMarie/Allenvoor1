package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication , Integer> {

    @Override
    Optional<Medication> findById(Integer integer);

    @Override
    List<Medication> findAll();

    @Override
    void deleteById(Integer integer);
}

