package com.wemakeitwork.allenvooreen.validator;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Autowired
    private MemberServiceInterface memberServiceInterface;

    @Override
    public boolean supports(Class<?> aClass) {
        return Member.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Member member = (Member) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (member.getUsername().length() < 3 || member.getUsername().length() > 32) {
            errors.rejectValue("memberName", "Size.userForm.username");
        }
        if (memberServiceInterface.findByMemberName(member.getUsername()).isPresent()) {
            errors.rejectValue("memberName", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (member.getPassword().length() < 3) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!member.getPasswordConfirm().equals(member.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
