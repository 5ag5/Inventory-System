package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OptionsGraphs {

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;
    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "type_of_graph")
    private String typeOfGraph;

    @Column(name = "Url_Api")
    private String urlAPI;

    @Column(name = "Title_Graph")
    private String titleGraph;

    @ElementCollection
    @CollectionTable(name = "options_entity_table", joinColumns = @JoinColumn(name = "options_graphs_id"))
    @Column(name = "option_entity")
    private List<String> optionsEntity;

    public OptionsGraphs(){}
    public OptionsGraphs(String entityType, String typeOfGraph, String urlAPI, String titleGraph){
        this.entityType = entityType;
        this.typeOfGraph = typeOfGraph;
        this.urlAPI = urlAPI;
        this.titleGraph = titleGraph;
        this.optionsEntity = new ArrayList<>();
    };

    public OptionsGraphs(String entityType, String typeOfGraph, List<String> listOptions, String urlAPI, String titleGraph){
        this.entityType = entityType;
        this.typeOfGraph = typeOfGraph;
        this.urlAPI = urlAPI;
        this.titleGraph = titleGraph;
        this.optionsEntity = listOptions;
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entity) {
        this.entityType = entity;
    }

    public String getTypeOfGraph() {
        return typeOfGraph;
    }

    public void setTypeOfGraph(String typeOfGraph) {
        this.typeOfGraph = typeOfGraph;
    }

    public List<String> getOptionsEntity() {
        return optionsEntity;
    }

    public void setOptionsEntity(List<String> optionsEntity) {
        this.optionsEntity = optionsEntity;
    }

    public String getUrlAPI() {
        return urlAPI;
    }

    public void setUrlAPI(String urlAPI) {
        this.urlAPI = urlAPI;
    }

    public void addOptionsList(String option){
        this.optionsEntity.add(option);
    }

    public String getTitleGraph() {
        return titleGraph;
    }

    public void setTitleGraph(String titleGraph) {
        this.titleGraph = titleGraph;
    }
}