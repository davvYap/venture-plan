package sg.edu.nus.iss.ventureplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ventureplan.model.Cost;
import sg.edu.nus.iss.ventureplan.repository.CostRepository;

@Service
public class CostService {
    @Autowired
    private CostRepository costRepo;

    public Cost save(final Cost cost) {
        return costRepo.save(cost);
    }

    public Cost findByVersion(final String version) {
        return costRepo.findByVersion(version);
    }

    public List<Cost> getAllCost() {
        return costRepo.getAllCost();
    }

    public List<String> getAllVersion() {
        return costRepo.getAllVersion();
    }

    public void deleteCost(final String version) {
        costRepo.deleteCost(version);
    }

    public Boolean isUniqueVersion(String costVersion) {
        List<String> versions = costRepo.getAllVersion();
        for (String version : versions) {
            if (version != null & version.contains(costVersion)) {
                return false;
            }
        }
        return true;
    }

}
