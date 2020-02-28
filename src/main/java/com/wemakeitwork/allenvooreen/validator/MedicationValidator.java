package com.wemakeitwork.allenvooreen.validator;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MedicationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Component
public class MedicationValidator implements Validator {

    @Autowired
    TeamRepository teamRepository;

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
        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Optional<Team> team = teamRepository.findById(teamId);

        if (team.isPresent()) {
            team.get().getMedicationList().stream()
                    .filter(x -> x.getMedicationName().equals(((Medication) o).getMedicationName()))
                    .forEach(x -> errors.rejectValue("medicationName", "Duplicate.userForm.medicationName"));


            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medicationName", "NotEmpty");
            if (medication.getMedicationName().length() < 3) {
                errors.rejectValue("medicationName", "Size.userForm.medicationName");
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medicationAmount", "NotEmpty");
            if (medication.getMedicationAmount() != null) {
                if (medication.getMedicationAmount() < 1) {
                    errors.rejectValue("medicationAmount", "Size.userForm.medicationAmount");
                }
            }
        }
    }
}
