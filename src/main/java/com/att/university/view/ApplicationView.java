package com.att.university.view;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationView {
    private final Scanner scanner;

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printMessage(Object object) {
        System.out.println(object);
    }

    public int readIntValue() {
        return Integer.parseInt(scanner.next());
    }

    public String readStringValue() {
        return scanner.next();
    }
}
