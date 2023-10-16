package inventarios.com.Sistema.Inventarios.ExcelFiles;

import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuditExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Audit> listAudit;

    public AuditExcelExporter(List<Audit> listAudit) {
        this.listAudit = listAudit;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Audit");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 2, "IP Address", style);
        createCell(row, 3, "Audit Date", style);
        createCell(row, 4, "Table ID", style);
        createCell(row, 5, "User ID", style);
        createCell(row, 6, "Table Name", style);
    }
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Audit audit : listAudit) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, audit.getId(), style);
            createCell(row, columnCount++, audit.getDireccionID(), style);
            createCell(row, columnCount++, audit.getFechaAuditoria(), style);
            createCell(row, columnCount++, audit.getIdTabla().toString(), style);
            createCell(row, columnCount++, audit.getIdUsuario(), style);
            createCell(row, columnCount++, audit.getNombreTabla(), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
        cell.setCellStyle(style);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }


}
