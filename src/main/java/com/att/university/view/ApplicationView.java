package com.att.university.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ApplicationView {
    private final Scanner scanner;

    public ApplicationView() {
        this.scanner = new Scanner(System.in);
    }

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
