package com.att.university.utility;

import com.att.university.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonUtilityTest {
    private final PersonUtility personUtility = new PersonUtility();

    @Test
    void createFullNameShouldReturnResultIfStudentExists() {

        Student student = Student.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .build();

        assertEquals("Fedor Tolov", personUtility.createFullName(student));
    }
}
