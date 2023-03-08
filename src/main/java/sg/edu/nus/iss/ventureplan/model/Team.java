package sg.edu.nus.iss.ventureplan.model;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Team implements Serializable {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 64, message = "Name must be between 3 and 64 characters")
    private String name;

    @NotEmpty(message = "Team number cannot be empty")
    @Size(min = 1, max = 64, message = "Team number cannot be empty")
    private String id = "1";

    private String teamName;

    @NotEmpty(message = "Number cannot be empty")
    @Min(value = 0, message = "Invalid number")
    private String siteSupervisor = "1";

    @NotEmpty(message = "Number cannot be empty")
    @Min(value = 0, message = "Invalid number")
    private String reo = "1";

    @NotEmpty(message = "Number cannot be empty")
    @Min(value = 0, message = "Invalid number")
    private String driver = "1";

    @NotEmpty(message = "Number cannot be empty")
    @Min(value = 2, message = "Cannot less than 2 worker")
    private String worker = "5";

    private boolean tool;
    private boolean machinery;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName() {
        this.teamName = this.name + "_" + this.id;
    }

    public String getSiteSupervisor() {
        return siteSupervisor;
    }

    public void setSiteSupervisor(String siteSupervisor) {
        this.siteSupervisor = siteSupervisor;
    }

    public String getReo() {
        return reo;
    }

    public void setReo(String reo) {
        this.reo = reo;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public boolean isTool() {
        return tool;
    }

    public void setTool(boolean tool) {
        this.tool = tool;
    }

    public boolean isMachinery() {
        return machinery;
    }

    public void setMachinery(boolean machinery) {
        this.machinery = machinery;
    }

    @Override
    public String toString() {
        return "Team [name=" + name + ", id=" + id + ", siteSupervisor=" + siteSupervisor + ", worker=" + worker
                + ", driver=" + driver + ", reo=" + reo + ", tool=" + tool + ", machinery=" + machinery + "]";
    }

}
