package inventarios.com.Sistema.Inventarios.PDFFiles;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import inventarios.com.Sistema.Inventarios.Models.Audit;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class AuditPDFExporter {
    private List<Audit> audits;

    public AuditPDFExporter(List<Audit> setAudits){
        this.audits = setAudits;
    }

    private void writeTableHeaders(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Audit ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Acion Audit", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("IP Address", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Audit Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Table ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Table Names", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(Audit audit:audits){
            table.addCell(String.valueOf(audit.getId()));
            table.addCell(String.valueOf(audit.getActionAudit()));
            table.addCell(audit.getDireccionID());
            table.addCell(String.valueOf(audit.getFechaAuditoria()));
            table.addCell(String.valueOf(audit.getIdTabla()));
            table.addCell(String.valueOf(audit.getIdUsuario()));
            table.addCell(String.valueOf(audit.getNombreTabla()));
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

        Paragraph p = new Paragraph("List of Audits", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        /*Image logo = new Image("/url.com");
        logo.setAlignment(Image.);*/


        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.0f,3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeaders(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
