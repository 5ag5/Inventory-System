package inventarios.com.Sistema.Inventarios.PDFFiles;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ProductPDFExporter {

    private Set<ProductDTO> productsLists;

    public ProductPDFExporter(Set <ProductDTO> setProducts){
        this.productsLists = setProducts;
    }

    private void writeTableHeaders(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Product ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description Product", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status Product", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cantidad Product", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio Compra", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio Venta", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Minimum Stock", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Includes IVA", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(ProductDTO product:productsLists){
            table.addCell(String.valueOf(product.getId()));
            table.addCell(String.valueOf(product.getDescriptionProduct()));
            table.addCell(String.valueOf(product.isStatusProduct()));
            table.addCell(String.valueOf(product.getCantidadProduct()));
            table.addCell(String.valueOf(product.getPrecioVenta()));
            table.addCell(String.valueOf(product.getMinimumStock()));
            table.addCell(String.valueOf(product.getMaximumStock()));
            table.addCell(String.valueOf(product.isIncludesIVA()));
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
