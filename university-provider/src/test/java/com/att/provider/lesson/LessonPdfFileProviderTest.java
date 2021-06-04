package com.att.provider.lesson;


import com.att.utility.PersonUtility;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.att.entity.AcademicRank;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LessonPdfFileProviderTest {
    private final PersonUtility personUtility = new PersonUtility();
    private final LessonPdfFileProvider fileProvider = new LessonPdfFileProviderImpl(personUtility);

    @Test
    void provideFileShouldReturnTheSameFile() throws Exception {
        List<Lesson> lessonList = getTestLessons();
        FileOutputStream outputStream = new FileOutputStream("./test.pdf");
        fileProvider.provideFile(outputStream, lessonList);

        PdfDocument actual = new PdfDocument(new PdfReader("lessons.pdf"));
        String firstPageContent = PdfTextExtractor.getTextFromPage(actual.getFirstPage(),
                new LocationTextExtractionStrategy());

        PdfDocument expected = new PdfDocument(new PdfReader("./test.pdf"));
        String firstPageContent2 = PdfTextExtractor.getTextFromPage(expected.getFirstPage(),
                new LocationTextExtractionStrategy());

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
