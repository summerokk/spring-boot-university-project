package com.att.university.controller;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.BuildingDao;
import com.att.university.dao.ClassroomDao;
import com.att.university.dao.CourseDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.LessonDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.StudentDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Student;
import com.att.university.entity.Teacher;
import com.att.university.view.ApplicationView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FrontController {
    private final ClassroomDao classroomDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final LessonDao lessonDao;
    private final ScienceDegreeDao scienceDegreeDao;
    private final AcademicRankDao academicRankDao;
    private final ApplicationView view;

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
            case 3:
                addLesson();
                break;
            case 4:
                removeLesson();
                break;
            case 5:
                showAllLessons();
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
        Optional<Group> group = groupDao.findById(groupId);

        studentDao.save(Student.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withGroup(group.get())
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
        view.printMessage(scienceDegreeDao.findAll(1, groupDao.count()));
        Integer scienceDegreeId = view.readIntValue();
        Optional<ScienceDegree> scienceDegree = scienceDegreeDao.findById(scienceDegreeId);

        view.printMessage("Enter academic rank id: ");
        view.printMessage(academicRankDao.findAll(1, groupDao.count()));
        Integer academicRankId = view.readIntValue();
        Optional<AcademicRank> academicRank = academicRankDao.findById(academicRankId);

        teacherDao.save(Teacher.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withLinkedin(linkedin)
                .withScienceDegree(scienceDegree.get())
                .withAcademicRank(academicRank.get())
                .build());

        view.printMessage("The teacher has been created");
    }

    private void addLesson() {
        view.printMessage("Enter course id: ");
        view.printMessage(courseDao.findAll(1, courseDao.count()));
        Integer courseId = view.readIntValue();
        Optional<Course> course = courseDao.findById(courseId);

        view.printMessage("Enter group id: ");
        view.printMessage(groupDao.findAll(1, groupDao.count()));
        Integer groupId = view.readIntValue();
        Optional<Group> group = groupDao.findById(groupId);

        view.printMessage("Enter teacher id: ");
        view.printMessage(teacherDao.findAll(1, teacherDao.count()));
        Integer teacherId = view.readIntValue();
        Optional<Teacher> teacher = teacherDao.findById(teacherId);

        view.printMessage("Enter date (format 2004-10-20T10:23): ");
        String lessonDate = view.readStringValue();

        view.printMessage("Enter classroom id: ");
        view.printMessage(classroomDao.findAll(1, teacherDao.count()));
        Integer classroomId = view.readIntValue();
        Optional<Classroom> classroom = classroomDao.findById(classroomId);

        lessonDao.save(Lesson.builder()
                .withCourse(course.get())
                .withGroup(group.get())
                .withTeacher(teacher.get())
                .withDate(LocalDateTime.parse(lessonDate))
                .withClassroom(classroom.get())
                .build());

        view.printMessage("The lesson has been created");
    }

    private void removeLesson() {
        view.printMessage("Enter lesson id: ");
        view.printMessage(lessonDao.findAll(1, lessonDao.count()));
        Integer lessonId = view.readIntValue();

        lessonDao.deleteById(lessonId);

        view.printMessage("The lesson has been deleted");
    }

    private void showAllLessons() {
        view.printMessage(lessonDao.findAll(1, lessonDao.count()));
    }

    private void printMenu() {
        view.printMessage("Choose the number to execute one of the following commands:\n" +
                "1. Add new student\n" +
                "2. Add new teacher\n" +
                "3. Add new lesson\n" +
                "4. Remove lesson by LESSON_ID\n" +
                "5. Show all lessons\n" +
                "0. Close application\n" +
                "Your number: ");
    }
}
