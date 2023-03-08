package sg.edu.nus.iss.ventureplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            System.out.println(">>>>> has error");
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
}
