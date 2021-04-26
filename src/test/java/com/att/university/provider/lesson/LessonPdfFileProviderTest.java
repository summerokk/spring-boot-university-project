package com.att.university.provider.lesson;

import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.utility.PersonUtility;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LessonPdfFileProviderTest {
    private final PersonUtility personUtility = new PersonUtility();
    private final LessonPdfFileProvider fileProvider = new LessonPdfFileProviderImpl(personUtility);

    @Test
    void test() throws Exception {
        List<Lesson> lessonList = getTestLessons();
        FileOutputStream outputStream = new FileOutputStream("./test2.pdf");
        fileProvider.provideFile(outputStream, lessonList);

        PdfDocument actual = new PdfDocument(new PdfReader("./test2.pdf"));
        String firstPageContent = PdfTextExtractor.getTextFromPage(actual.getFirstPage(), new LocationTextExtractionStrategy());

        PdfDocument expected = new PdfDocument(new PdfReader("./test.pdf"));
        String firstPageContent2 = PdfTextExtractor.getTextFromPage(expected.getFirstPage(), new LocationTextExtractionStrategy());

        assertEquals(firstPageContent2, firstPageContent);
    }

    private List<Lesson> getTestLessons() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher teacher = Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        Course course = new Course(1, "Special Topics in Agronomy");
        Course course2 = new Course(2, "Math");

        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));
        Group group2 = new Group(2, "HT-22", new Faculty(2, "Department of Geography"));

        Classroom classroom = new Classroom(1, 12, new Building(1, "Kirova 32"));

        return Arrays.asList(
                Lesson.builder()
                        .withId(1)
                        .withCourse(course)
                        .withGroup(group)
                        .withTeacher(teacher)
                        .withDate(LocalDateTime.parse("2004-10-19T10:23"))
                        .withClassroom(classroom)
                        .build(),
                Lesson.builder()
                        .withId(2)
                        .withCourse(course2)
                        .withGroup(group2)
                        .withTeacher(teacher)
                        .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                        .withClassroom(classroom)
                        .build()
        );
    }
}