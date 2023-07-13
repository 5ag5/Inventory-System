package inventarios.com.Sistema.Inventarios.PDFFiles;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import inventarios.com.Sistema.Inventarios.DTOs.UserDTO;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class UserPDFExporter {
    private Set<UserDTO> userList;
    public UserPDFExporter(Set <UserDTO> setUsers){
        this.userList = setUsers;
    }

    private void writeTableHeaders(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User Login", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Password", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date Registered", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Reg Date", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(UserDTO user:userList){
            table.addCell(String.valueOf(user.getId()));
            table.addCell(String.valueOf(user.getLogin()));
            table.addCell(String.valueOf(user.getLastName()));
            table.addCell(String.valueOf(user.getPassword()));
            table.addCell(String.valueOf(user.getEmail()));
            table.addCell(String.valueOf(user.isStatus()));
            table.addCell(String.valueOf(user.getDateRegistered()));
            table.addCell(String.valueOf(user.getLastRegisteredPassword()));
        }
    }

    public void export(HttpServletResponse response) throws IOException
    {
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

        Paragraph p = new Paragraph("List of Products", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        /*Image logo = new Image("/url.com");
        logo.setAlignment(Image.);*/


        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.0f,3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeaders(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
