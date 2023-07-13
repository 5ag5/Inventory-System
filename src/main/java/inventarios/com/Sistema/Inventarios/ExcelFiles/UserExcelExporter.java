package inventarios.com.Sistema.Inventarios.ExcelFiles;

import inventarios.com.Sistema.Inventarios.Models.*;
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

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UserInventory> listUsers;
    private List<Audit> listAudit;
    private List<Category> listCategory;

    private String typeOfGraph;

    public UserExcelExporter(List<UserInventory> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    /*public UserExcelExporter(List<Product> listProduct, String typeOfGraphProduct) {
        this.listProduct = listProduct;
        workbook = new XSSFWorkbook();
        this.typeOfGraph = typeOfGraphProduct;
    }*/


    private void writeHeaderLine() {
        /*sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Roles", style);
        createCell(row, 4, "Enabled", style);*/

        sheet = workbook.createSheet("User");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "Login", style);
        createCell(row, 2, "Last Name", style);
        createCell(row, 3, "Password", style);
        createCell(row, 4, "E-mail", style);
        createCell(row, 5, "Date Registered", style);
        createCell(row, 6, "Last Registered Password", style);

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

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UserInventory user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getLogin(), style);
            createCell(row, columnCount++, user.getLastName(), style);
            createCell(row, columnCount++, user.getPassword().toString(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.isStatus(), style);
            createCell(row, columnCount++, user.getDateRegistered(), style);
            createCell(row, columnCount++, user.getLastRegisteredPassword(), style);
        }
    }


    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

    private void createHeadersUserInventory(String typeOfGraphHeader) {

    }

    private void createHeadersProductInventory(String product) {

    }

    private void createHeadersAuditInventory(String audit) {

    }

    private void createHeadersCategoryInventory(String category) {

    }

    private void createHeadersParametersInventory(String Parameter) {

    }

    private void createContentParametersInventory() {

    }

        private void createContentsUserInventory () {
            int rowCount = 1;

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);
            style.setFont(font);

            for (UserInventory user : listUsers) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, user.getId(), style);
                createCell(row, columnCount++, user.getLogin(), style);
                createCell(row, columnCount++, user.getLastName(), style);
                createCell(row, columnCount++, user.getPassword().toString(), style);
                createCell(row, columnCount++, user.getEmail(), style);
                createCell(row, columnCount++, user.isStatus(), style);
                createCell(row, columnCount++, user.getDateRegistered(), style);
                createCell(row, columnCount++, user.getLastRegisteredPassword(), style);
            }
        }

    private void createContentAuditInventory() {
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

    private void createContentCategoryInventory() {


    }

    private void createContentProductInventory() {

    }

}
