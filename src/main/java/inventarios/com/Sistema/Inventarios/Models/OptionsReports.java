package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class OptionsReports {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @Column(name="entity_Type")
    private String entityType;
    @Column(name="report_Type")
    private String reportType;
    @Column(name="url_Type")
    private String urlApi;
    @Column(name="file_Name")
    private String fileName;

    public OptionsReports(){}

    public OptionsReports(String entityType, String reportType, String urlApi, String fileName){
        this.entityType = entityType;
        this.reportType = reportType;
        this.urlApi = urlApi;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getUrlType() {
        return urlApi;
    }

    public void setUrlType(String urlType) {
        this.urlApi = urlType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
