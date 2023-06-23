package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.UserType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private String login;
    private String lastName;
    private String password;
    private String email;
    private boolean status;
    private LocalDate dateRegistered;
    private LocalDate lastRegisteredPassword;
    private UserType userType;
    private int numberOfLoginTries;
    private Set<AuditDTO> auditories = new HashSet<>();

    public UserDTO(){}

    public UserDTO(UserInventory userInventory) {
        this.login = userInventory.getLogin();
        this.lastName = userInventory.getLastName();
        this.password = userInventory.getPassword();
        this.email = userInventory.getEmail();
        this.status = userInventory.isStatus();
        this.dateRegistered = userInventory.getDateRegistered();
        this.lastRegisteredPassword = userInventory.getLastRegisteredPassword();
        this.userType = userInventory.getUserType();
        this.numberOfLoginTries = userInventory.getNumberOfLoginTries();
        this.auditories = userInventory.getAuditories().stream().map(audit -> new AuditDTO(audit)).collect(Collectors.toSet());
    }

    public String getLogin() {
        return login;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isStatus() {
        return status;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public LocalDate getLastRegisteredPassword() {
        return lastRegisteredPassword;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getNumberOfLoginTries() {
        return numberOfLoginTries;
    }

    public Set<AuditDTO> getAuditories() {
        return auditories;
    }
}
