package com.att.university.controller;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.BuildingDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.StudentDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Group;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Student;
import com.att.university.entity.Teacher;
import com.att.university.view.ApplicationView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FrontController {
    private final BuildingDao buildingDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final GroupDao groupDao;
    private final ScienceDegreeDao scienceDegreeDao;
    private final AcademicRankDao academicRankDao;
    private final ApplicationView view;

    public FrontController(BuildingDao buildingDao, StudentDao studentDao, TeacherDao teacherDao, GroupDao groupDao,
                           ScienceDegreeDao scienceDegreeDao, AcademicRankDao academicRankDao, ApplicationView view) {
        this.buildingDao = buildingDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
        this.scienceDegreeDao = scienceDegreeDao;
        this.academicRankDao = academicRankDao;
        this.view = view;
    }

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
            case 2:
                addTeacher();
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
        view.printMessage(groupDao.findAll(1, groupDao.count()));
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

    private void addTeacher() {
        view.printMessage("Enter teacher's firstName: ");
        String firstName = view.readStringValue();

        view.printMessage("Enter teacher's lastName: ");
        String lastName = view.readStringValue();

        view.printMessage("Enter teacher's email: ");
        String email = view.readStringValue();

        view.printMessage("Enter teacher's password: ");
        String password = view.readStringValue();

        view.printMessage("Enter teacher's linkedin: ");
        String linkedin = view.readStringValue();

        view.printMessage("Enter science degree id: ");
        System.out.println(scienceDegreeDao.findAll(1, groupDao.count()));
        Integer scienceDegreeId = view.readIntValue();
        ScienceDegree scienceDegree = scienceDegreeDao.findById(scienceDegreeId);

        view.printMessage("Enter academic rank id: ");
        System.out.println(academicRankDao.findAll(1, groupDao.count()));
        Integer academicRankId = view.readIntValue();
        AcademicRank academicRank = academicRankDao.findById(academicRankId);

        teacherDao.save(Teacher.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withLinkedin(linkedin)
                .withScienceDegree(scienceDegree)
                .withAcademicRank(academicRank)
                .build());

        view.printMessage("The teacher has been created");
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
