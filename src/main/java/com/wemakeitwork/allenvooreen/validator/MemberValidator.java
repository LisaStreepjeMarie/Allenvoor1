package com.wemakeitwork.allenvooreen.validator;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Member.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Member member = (Member) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (member.getUsername().length() < 6 || member.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (memberService.findByMembername(member.getUsername()).isPresent()) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (member.getPassword().length() < 8 || member.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!member.getPasswordConfirm().equals(member.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
