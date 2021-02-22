package com.att.university.validator.impl;

import com.att.university.entity.Student;
import com.att.university.validator.StudentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component("studentValidator")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentValidatorImpl implements StudentValidator {
    private static final Pattern EMAIL_REGEX_EXPRESSION = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static final int MIN_PASSWORD_LENGTH = 6;

    public void validate(Student student) {
        if (student.getFirstName() == null) {
            throw new RuntimeException("First name is null");
        }

        if (student.getLastName() == null) {
            throw new RuntimeException("Last name is null");
        }

        if (student.getEmail() == null) {
            throw new RuntimeException("Email is null");
        }

        if (student.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }

        if (student.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be 6 or more characters");
        }

        if (!EMAIL_REGEX_EXPRESSION.matcher(student.getEmail()).find()) {
            throw new RuntimeException("Email is incorrect");
        }
    }
}
