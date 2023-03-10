package sg.edu.nus.iss.ventureplan.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Workdone implements Serializable {
    @NotNull(message = "Type cannot be null")
    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Quantity cannot be null")
    @NotEmpty(message = "Quantity cannot be empty")
    @Size(min = 1, max = 64, message = "Quantiy must be > 0")
    private String quantity;

    @NotNull(message = "Date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfWork;

    @NotNull(message = "Team cannot be null")
    @NotEmpty(message = "Team cannot be empty")
    private String team;

    private String workdoneId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public LocalDate getDateOfWork() {
        return dateOfWork;
    }

    public void setDateOfWork(LocalDate dateOfWork) {
        this.dateOfWork = dateOfWork;
    }

    public String getWorkdoneId() {
        return workdoneId;
    }

    public void setWorkdoneId() {
        this.workdoneId = this.dateOfWork.toString() + "_" + this.team;
    }

    @Override
    public String toString() {
        return "Workdone [type=" + type + ", quantity=" + quantity + ", team=" + team + "]";
    }

}
