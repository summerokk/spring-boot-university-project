package com.att.utility;

import com.att.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonUtility {
    public String createFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }
}
