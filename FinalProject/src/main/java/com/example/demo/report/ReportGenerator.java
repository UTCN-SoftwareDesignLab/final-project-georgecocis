package com.example.demo.report;

import com.example.demo.movie.model.MovieDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportGenerator {
    public void export(List<MovieDTO> movieDTOList) throws IOException {
        try (PDDocument pdfDoc = new PDDocument()) {

            PDPage myPage = new PDPage();
            pdfDoc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(pdfDoc, myPage)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 30);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Income per movie report.");
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.newLine();
                cont.newLine();

                for (MovieDTO movie : movieDTOList){
                    cont.showText("Title: " + movie.getTitle());
                    cont.newLine();

                    cont.showText("Income: " + movie.getIncome());
                    cont.newLine();

                    cont.newLine();
                }
                cont.endText();
            }
            pdfDoc.save("src/main/resources/moviesIncome.pdf");
        }
    }
}
