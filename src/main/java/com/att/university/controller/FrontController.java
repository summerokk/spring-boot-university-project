package com.att.university.controller;

import com.att.university.dao.BuildingDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.view.ApplicationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FrontController {
    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ApplicationView view;

    public void runApplication() {
        int command;

        do {
            printMenu();

            command = view.readIntValue();
            runCommand(command);
            view.printMessage(command);
        } while (command != 0);
    }

    private void runCommand(int command) {
        switch (command) {
            case 1:
                addStudent();
                break;
            case 0:
                view.printMessage("Good Bye!");
                break;
            default:
                view.printMessage("Command not found!");
        }
    }

    private void addStudent() {
        view.printMessage("Enter student's firstName: ");
        String firstName = view.readStringValue();

        view.printMessage("Enter student's lastName: ");
        String lastName = view.readStringValue();

        view.printMessage("Enter student's email: ");
        String email = view.readStringValue();

        view.printMessage("Enter student's password: ");
        String password = view.readStringValue();

        view.printMessage("Enter student's group id: ");
        System.out.println(groupDao.findAll());
        Integer groupId = view.readIntValue();
        Group group = groupDao.findById(groupId);

        studentDao.save(Student.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withGroup(group)
                .build());

        view.printMessage("The student has been created");
    }

    private void printMenu() {
        view.printMessage("Choose the number to execute one of the following commands:\n" +
                "1. Add new student\n" +
                "2. Add new teacher\n" +
                "3. Add new lesson\n" +
                "4. Remove lesson by LESSON_ID\n" +
                "5. Show all lessons\n" +
                "6. Show teacher lessons by TEACHER_ID\n" +
                "0. Close application\n" +
                "Your number: ");
    }
}
