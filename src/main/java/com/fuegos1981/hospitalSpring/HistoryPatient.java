package com.fuegos1981.hospitalSpring;

import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.model.Patient;
import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.SortRule;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.context.MessageSource;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class HistoryPatient {
    private static final Logger logger = LogManager.getLogger();
    private AppointmentService appointmentService;
    private MessageSource messageSource;


    public HistoryPatient(AppointmentService appointmentService, MessageSource messageSource) {
        this.appointmentService = appointmentService;
        this.messageSource = messageSource;
    }

    public void getHistoryPatient(Patient patient, File file, Locale locale){
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        try {
            PDDocumentInformation pdi = document.getDocumentInformation();

            pdi.setTitle("Hospital");
            pdi.setCreator("Patient's history");
            Calendar date = Calendar.getInstance();
            pdi.setCreationDate(date);
            pdi.setModificationDate(date);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            ClassLoader classLoader = HistoryPatient.class.getClassLoader();
            PDFont font = PDType0Font.load( document, new File(classLoader.getResource("fonts/times.ttf").getFile()));
            contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.setLeading(50.5f);
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText(messageSource.getMessage("History",new Object[]{}, locale));
            contentStream.newLine();
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.setFont(font, 16);
            contentStream.setLeading(20.5f);
            contentStream.showText(messageSource.getMessage("Name",new Object[]{}, locale)+": "+ patient.toString());
            contentStream.newLine();
            contentStream.showText(messageSource.getMessage("Birthday",new Object[]{}, locale)+": "+patient.getBirthdayString());
            contentStream.newLine();
            contentStream.showText(messageSource.getMessage("Email",new Object[]{}, locale)+": "+ patient.getEmail());
            contentStream.newLine();
            contentStream.showText(messageSource.getMessage("Gender",new Object[]{}, locale)+": "+patient.getGender().toString().toLowerCase(Locale.ROOT));
            contentStream.newLine();
            contentStream.endText();
            getTable(contentStream,page, patient,font, locale);
            contentStream.close();

            document.save(file);
            document.close();
        }
        catch (IOException e){
            logger.error(e.getMessage());
        }
    }
    private  void getTable(PDPageContentStream contentStream, PDPage page, Patient patient,PDFont font, Locale locale) throws IOException{
        Map<String,Object> selection = new HashMap<>();
        selection.put("patient.id",patient.getId());
        List<AppointmentDto> list =appointmentService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS,selection, SortRule.DATE_CREATE_DESC, null));
        int pageHeight = (int)page.getTrimBox().getHeight()-200; //get height of the page

        contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.setLineWidth(1);

        int initX = 50;
        int initY = pageHeight-50;
        int cellHeight = 20;
        int cellWidth = 120;

        int colCount = 4;
        int rowCount = list.size();

        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,messageSource.getMessage("Date",new Object[]{}, locale), font);
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,messageSource.getMessage("Doctor",new Object[]{}, locale), font);
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,messageSource.getMessage("Diagnosis",new Object[]{}, locale), font);
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth+30,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,messageSource.getMessage("Description",new Object[]{}, locale), font);
        initX = 50;
        initY -=cellHeight;
        for(int i = 1; i<=rowCount;i++){
            for(int j = 1; j<=colCount;j++){
                if(j == 4){
                    contentStream.addRect(initX,initY,cellWidth+30,-cellHeight);
                    writeApp(contentStream, list, initX, initY, cellHeight, i, j, font);
                    initX+=cellWidth+30;
                }else{
                    contentStream.addRect(initX,initY,cellWidth,-cellHeight);
                    writeApp(contentStream, list, initX, initY, cellHeight, i, j, font);
                    initX+=cellWidth;
                }
            }
            initX = 50;
            initY -=cellHeight;
        }

        contentStream.stroke();
    }

    private static void writeApp(PDPageContentStream contentStream, List<AppointmentDto> list, int initX, int initY, int cellHeight, int i, int j,PDFont font) throws IOException {
        if (j ==1)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDateCreate().toString(), font);
        if (j ==2)
            writeTextInTable(contentStream, initX, initY, cellHeight,
                    list.get(i -1).getDoctorName(), font);
        if (j ==3)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDiagnosisName(), font);
        if (j ==4)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDescription(), font);
    }

    private static void writeTextInTable(PDPageContentStream contentStream, int initX, int initY, int cellHeight, String string,PDFont font) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(initX+10,initY-cellHeight+10);
        contentStream.setFont(font,10);
        contentStream.showText(string);
        contentStream.endText();
    }

}
