package com.att.provider.lesson;

import com.att.utility.PersonUtility;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.att.entity.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonPdfFileProviderImpl implements LessonPdfFileProvider {
    private static final String LESSON_DATE_PATTERN = "dd.MM.yyyy HH:mm";
    private static final String[] TABLE_HEADERS = {"ID", "Group", "Location", "Classroom", "Teacher", "Date"};
    private static final int COLUMNS_COUNT = 6;

    private final PersonUtility personUtility;

    @Override
    public void provideFile(OutputStream outputStream, List<Lesson> lessons) {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        Table table = new Table(COLUMNS_COUNT);
        table.setWidth(UnitValue.createPercentValue(100));

        writeTableHeader(table);
        writeTableData(table, lessons);

        document.add(table);
        document.close();
    }

    private void writeTableHeader(Table table) {
        Stream.of(TABLE_HEADERS).forEach(columnTitle -> {
            Cell cell = new Cell().add(new Paragraph(columnTitle));
            cell.setBackgroundColor(new DeviceGray(0.75f));
            table.addHeaderCell(cell);
        });
    }

    private void writeTableData(Table table, List<Lesson> lessons) {
        lessons.forEach(lesson -> {
            table.addCell(lesson.getId().toString());
            table.addCell(lesson.getGroup().getName());
            table.addCell(lesson.getClassroom().getBuilding().getAddress());
            table.addCell(lesson.getClassroom().getNumber().toString());
            table.addCell(personUtility.createFullName(lesson.getTeacher()));
            table.addCell(lesson.getDate().format(DateTimeFormatter.ofPattern(LESSON_DATE_PATTERN)));
        });
    }
}
