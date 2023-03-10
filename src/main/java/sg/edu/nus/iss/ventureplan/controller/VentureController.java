package sg.edu.nus.iss.ventureplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import sg.edu.nus.iss.ventureplan.model.Team;
import sg.edu.nus.iss.ventureplan.service.VentureService;

@Controller
@RequestMapping(path = "/create")
public class VentureController {

    @Autowired
    private VentureService ventureService;

    @GetMapping
    public String createTeam(Model model, @ModelAttribute Team team) {
        model.addAttribute("team", team);
        return "create";
    }

    @PostMapping
    public String showTeam(Model model, @ModelAttribute @Valid Team team, BindingResult bindings) {
        if (bindings.hasErrors()) {
            System.out.println("Team creation >>>>> has error");
            return "create";
        }
        team.setTeamName();

        // check whether team name is unique
        if (!ventureService.isUniqueTeamName(team.getTeamName())) {
            ObjectError err = new ObjectError("globalError",
                    "%s team exist in database.".formatted(team.getTeamName()));
            bindings.addError(err);
            return "create";
        }
        ventureService.save(team);

        model.addAttribute("hasTool", team.isTool());
        model.addAttribute("hasMachinery", team.isMachinery());
        return "team";
    }

    @GetMapping(path = "/teams")
    public String showAllTeams(Model model) {
        List<Team> allTeams = ventureService.findAllTeam();
        model.addAttribute("allTeams", allTeams);
        return "teams";
    }

    // query string
    @GetMapping(path = "/team")
    public String getTeamByQueryStr(Model model, @RequestParam(defaultValue = "David_1") String teamName) {
        Team selectedTeam = ventureService.findByTeamName(teamName);
        model.addAttribute("team", selectedTeam);
        return "team";
    }

    // path variable
    @GetMapping(path = "{teamName}")
    public String getTeamByPathVar(Model model, @PathVariable String teamName) {
        Team selectedTeam = ventureService.findByTeamName(teamName);
        model.addAttribute("team", selectedTeam);
        model.addAttribute("hasTool", selectedTeam.isTool());
        model.addAttribute("hasMachinery", selectedTeam.isMachinery());
        return "team";
    }

    // DELETE
    @GetMapping(path = "/delete/{teamName}")
    public String deleteByTeamName(Model model, @PathVariable String teamName) {
        System.out.println("Deleted team >>> " + teamName);
        ventureService.deleteTeam(teamName);

        List<Team> allTeams = ventureService.findAllTeam();
        model.addAttribute("allTeams", allTeams);
        return "teams";
    }

    // EDIT team
    @GetMapping(path = "/edit/{teamName}")
    public String editTeam(Model model, @PathVariable String teamName) {
        Team team = ventureService.findByTeamName(teamName);
        model.addAttribute("team", team);
        return "editTeam";
    }

    // EDIT team
    @PostMapping(path = "/edit")
    public String updateTeam(Model model, @ModelAttribute @Valid Team team, BindingResult bindings) {
        if (bindings.hasErrors()) {
            System.out.println("Team update >>>>> has error");
            return "editTeam";
        }
        team.setTeamName();
        if (!ventureService.isUniqueTeamName(team.getTeamName())) {
            ventureService.deleteTeam(team.getTeamName());
        }
        ventureService.save(team);
        System.out.printf("Team %s is updated !!! \n", team.getTeamName());
        model.addAttribute("hasTool", team.isTool());
        model.addAttribute("hasMachinery", team.isMachinery());
        return "team";
    }
}
