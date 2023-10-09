package inventarios.com.Sistema.Inventarios.ExcelFiles;

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

public class ProductExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> listProduct;

    public ProductExcelExporter(List<Product> listProduct) {
        this.listProduct = listProduct;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Products");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "Description Product", style);
        createCell(row, 2, "Status Product", style);
        createCell(row, 3, "Quantity Product", style);
        createCell(row, 4, "Bought Price", style);
        createCell(row, 5, "Sell Price", style);
        createCell(row, 6, "Minimum Stock", style);
        createCell(row, 7, "Maximum Stock", style);
        createCell(row, 8, "Includes IVA", style);
    }
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Product product : listProduct) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, product.getId(), style);
            createCell(row, columnCount++, product.getDescriptionProduct(), style);
            createCell(row, columnCount++, product.isStatusProduct(), style);
            createCell(row, columnCount++, product.getCantidadProduct(), style);
            createCell(row, columnCount++, product.getPrecioCompra(), style);
            createCell(row, columnCount++, product.getPrecioVenta(), style);
            createCell(row, columnCount++, product.getMinimumStock(), style);
            createCell(row, columnCount++, product.getMaximumStock(), style);
            createCell(row, columnCount++, product.isIncludesIVA(), style);
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
