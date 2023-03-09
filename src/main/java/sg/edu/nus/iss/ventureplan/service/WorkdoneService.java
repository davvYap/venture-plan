package sg.edu.nus.iss.ventureplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ventureplan.model.Workdone;
import sg.edu.nus.iss.ventureplan.repository.WorkdoneRepository;

@Service
public class WorkdoneService {
    @Autowired
    private WorkdoneRepository wdRepo;

    public Workdone save(final Workdone workdone) {
        return wdRepo.save(workdone);
    }

    public Workdone findByWorkdoneId(final String workdoneId) {
        return wdRepo.findByWorkdoneId(workdoneId);
    }

    public List<Workdone> findAllWorkdone() {
        return wdRepo.findAllWorkdone();
    }

    public List<String> getWorkdoneIds() {
        return wdRepo.getWorkdoneIds();
    }
}
