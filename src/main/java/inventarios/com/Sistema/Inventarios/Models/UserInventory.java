package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class UserInventory {

    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy = "native")
    @Id
    private Long id;
    private String login;
    private String lastName;
    private String password;
    private String email;
    private boolean status;
    private LocalDate dateRegistered;
    private LocalDate lastRegisteredPassword;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private int numberOfLoginTries;
    @OneToMany(mappedBy = "userInventory", fetch=FetchType.EAGER)
    private Set<Audit> auditories = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Parameter parameter;

    public UserInventory(){}

    public UserInventory(String login, String lastName, String password, String email, UserType userType) {
        this.login = login;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.status = true;
        this.dateRegistered = LocalDate.now();
        this.lastRegisteredPassword = LocalDate.now();
        this.userType = userType;
        this.numberOfLoginTries = 0;
    }

    /*public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public LocalDate getLastRegisteredPassword() {
        return lastRegisteredPassword;
    }

    public void setLastRegisteredPassword(LocalDate lastRegisteredPassword) {
        this.lastRegisteredPassword = lastRegisteredPassword;
    }

    public UserType getUserType() {
        return userType;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getNumberOfLoginTries() {
        return numberOfLoginTries;
    }

    public void setNumberOfLoginTries(int numberOfLoginTries) {
        this.numberOfLoginTries = numberOfLoginTries;
    }

    public Set<Audit> getAuditories() {
        return auditories;
    }

    public void setAuditories(Set<Audit> auditories) {
        this.auditories = auditories;
    }

    public void addAudit(Audit audit){
        audit.setUser(this);
        auditories.add(audit);
    }
}
