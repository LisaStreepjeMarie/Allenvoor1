package com.wemakeitwork.allenvooreen.validator;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.service.MedicationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;


@Component
public class MedicationValidator implements Validator {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private MedicationServiceInterface medicationServiceInterface;

    @Override
    public boolean supports(Class<?> aClass) {
        return Medication.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Medication medication = (Medication) o;
        Team team = (Team) httpSession.getAttribute("team");
        medication.setTeam(team);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medicationName", "NotEmpty");
        if (medication.getMedicationName().length() < 3 ) {
            errors.rejectValue("medicationName", "Size.userForm.medicationName");
        }
        if (medicationServiceInterface.findByMedicationName(medication.getMedicationName()).isPresent()) {
            errors.rejectValue("medicationName", "Duplicate.userForm.medicationName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medicationAmount", "NotEmpty");
        if (medication.getMedicationAmount() < 1) {
            errors.rejectValue("medicationAmount", "Size.userForm.medicationAmount");
        }
    }
}
