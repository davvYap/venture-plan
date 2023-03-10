package sg.edu.nus.iss.ventureplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ventureplan.model.Revenue;
import sg.edu.nus.iss.ventureplan.repository.RevenueRepository;

@Service
public class RevenueService {
    @Autowired
    private RevenueRepository revRepo;

    public Revenue save(final Revenue revenue) {
        return revRepo.save(revenue);
    }

    public Revenue findByVersion(final String version) {
        return revRepo.findByVersion(version);
    }

    public List<Revenue> getAllRevenue() {
        return revRepo.getAllRevenue();
    }

    public List<String> getAllVersion() {
        return revRepo.getAllVersion();
    }

    public void deleteRevenue(final String version) {
        revRepo.deleteRevenue(version);
    }

}
