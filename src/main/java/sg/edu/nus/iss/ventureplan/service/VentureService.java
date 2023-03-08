package sg.edu.nus.iss.ventureplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ventureplan.model.Team;
import sg.edu.nus.iss.ventureplan.repository.VentureRepository;

@Service
public class VentureService {
    @Autowired
    private VentureRepository ventureRepo;

    public Team save(final Team team) {
        return ventureRepo.save(team);
    }

    public Team findByTeamName(final String teamName) {
        return ventureRepo.findByTeamName(teamName);
    }

    public List<Team> findAllTeam() {
        return ventureRepo.findAllTeam();
    }

    public List<String> getTeamNames() {
        return ventureRepo.getTeamNames();
    }

    public Boolean isUniqueTeamName(String teamName) {
        List<String> teamNames = ventureRepo.getTeamNames();
        teamNames.stream().forEach(t -> System.out.println(t));
        for (String name : teamNames) {
            if (name != null & name.contains(teamName)) {
                return false;
            }
        }
        return true;
    }
}
