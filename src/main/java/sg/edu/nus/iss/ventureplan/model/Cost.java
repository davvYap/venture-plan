package sg.edu.nus.iss.ventureplan.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Cost implements Serializable {
    @NotNull(message = "Version cannot be null")
    @NotEmpty(message = "Version cannot be empty")
    @Size(min = 4, max = 64, message = "Version must be between 4 and 64 characters, *year_no*")
    private String version = "2022_1";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String siteSupervisorCost = "130";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String reoCost = "135";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String driverCost = "120";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String workerCost = "90";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String toolCost = "200";

    @NotNull(message = "Cost cannot be null")
    @NotEmpty(message = "Cost cannot be empty")
    @Size(min = 1, max = 64, message = "Cost must be between 1 and 64 characters")
    private String machineryCost = "500";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSiteSupervisorCost() {
        return siteSupervisorCost;
    }

    public void setSiteSupervisorCost(String siteSupervisorCost) {
        this.siteSupervisorCost = siteSupervisorCost;
    }

    public String getReoCost() {
        return reoCost;
    }

    public void setReoCost(String reoCost) {
        this.reoCost = reoCost;
    }

    public String getDriverCost() {
        return driverCost;
    }

    public void setDriverCost(String driverCost) {
        this.driverCost = driverCost;
    }

    public String getWorkerCost() {
        return workerCost;
    }

    public void setWorkerCost(String workerCost) {
        this.workerCost = workerCost;
    }

    public String getToolCost() {
        return toolCost;
    }

    public void setToolCost(String toolCost) {
        this.toolCost = toolCost;
    }

    public String getMachineryCost() {
        return machineryCost;
    }

    public void setMachineryCost(String machineryCost) {
        this.machineryCost = machineryCost;
    }

}
