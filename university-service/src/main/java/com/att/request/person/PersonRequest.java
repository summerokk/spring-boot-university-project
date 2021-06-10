package com.att.request.person;

import com.att.validator.Username;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class PersonRequest {
    @Username
    private String firstName;
    @Username(message = "{lastname.field}")
    private String lastName;
    @Email(message = "{email.field}")
    private String email;
}
