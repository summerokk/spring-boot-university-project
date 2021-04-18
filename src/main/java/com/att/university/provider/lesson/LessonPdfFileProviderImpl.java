package com.att.university.provider.lesson;

import com.att.university.entity.Lesson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Component
public class LessonPdfFileProviderImpl implements LessonPdfFileProvider {
    private static final String FILE_NAME_PATTERN = "yyyy-MM-dd_HH:mm";
    private static final String CONTENT_TYPE = "application/pdf";
    private static final String HEADER_KEY = "Content-Disposition";
    private static final String HEADER_VALUE = "attachment; filename=lessons_%s.pdf";
    private static final String LESSON_DATE_PATTERN = "dd.MM.yyyy HH:mm";
    private static final String[] TABLE_HEADERS = {"ID", "Group", "Location", "Classroom", "Teacher", "Date"};
    private static final int COLUMNS_COUNT = 6;

    @Override
    public void provideFile(HttpServletResponse response, List<Lesson> lessons) throws Exception {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEADER_KEY, String.format(HEADER_VALUE, generateFileName()));

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        PdfPTable table = new PdfPTable(COLUMNS_COUNT);

        writeTableHeader(table);
        writeTableData(table, lessons);

        document.add(table);
        document.close();
    }

    private String generateFileName() {
        DateFormat dateFormatter = new SimpleDateFormat(FILE_NAME_PATTERN);

        return dateFormatter.format(new Date());
    }

    private void writeTableHeader(PdfPTable table) {
        Stream.of(TABLE_HEADERS).forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void writeTableData(PdfPTable table, List<Lesson> lessons) {
        lessons.forEach(lesson -> {
            table.addCell(lesson.getId().toString());
            table.addCell(lesson.getGroup().getName());
            table.addCell(lesson.getClassroom().getBuilding().getAddress());
            table.addCell(lesson.getClassroom().getNumber().toString());
            table.addCell(lesson.getTeacher().getFullName());
            table.addCell(lesson.getDate().format(DateTimeFormatter.ofPattern(LESSON_DATE_PATTERN)));
        });
    }
}
