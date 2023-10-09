package inventarios.com.Sistema.Inventarios.PDFFiles;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import inventarios.com.Sistema.Inventarios.DTOs.CategoryDTO;
import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ParameterPDFExporter {
    private Set<ParameterDTO> parameterList;

    public ParameterPDFExporter(Set <ParameterDTO> setParameters){
        this.parameterList = setParameters;
    }

    private void writeTableHeaders(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Parameter ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Parameter Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Parameter Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name Parameter", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Value Parameter", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(ParameterDTO parameter:parameterList){
            table.addCell(String.valueOf(parameter.getId()));
            table.addCell(String.valueOf(parameter.getParameterDescription()));
            table.addCell(String.valueOf(parameter.isParameterStatus()));
            table.addCell(String.valueOf(parameter.getNameParameter()));
            table.addCell(String.valueOf(parameter.getValueParameter()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DecimalFormat decFormatter = new DecimalFormat("0.00");

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font1.setSize(15);
        font1.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Categories", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        /*Image logo = new Image("/url.com");
        logo.setAlignment(Image.);*/


        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f,3.0f,3.0f});
        table.setSpacingBefore(10);

        writeTableHeaders(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
