package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication , Integer> {
}
