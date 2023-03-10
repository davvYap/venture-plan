package sg.edu.nus.iss.ventureplan.model;

import java.io.Serializable;

public class Revenue implements Serializable {
    private String version;
    private String supplyTeamRate;
    private String pipeLayingRate;

    public String getSupplyTeamRate() {
        return supplyTeamRate;
    }

    public void setSupplyTeamRate(String supplyTeamRate) {
        this.supplyTeamRate = supplyTeamRate;
    }

    public String getPipeLayingRate() {
        return pipeLayingRate;
    }

    public void setPipeLayingRate(String pipeLayingRate) {
        this.pipeLayingRate = pipeLayingRate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
