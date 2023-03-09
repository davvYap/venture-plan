package sg.edu.nus.iss.ventureplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.ventureplan.model.Team;
import sg.edu.nus.iss.ventureplan.model.Workdone;
import sg.edu.nus.iss.ventureplan.service.VentureService;
import sg.edu.nus.iss.ventureplan.service.WorkdoneService;

@Controller
@RequestMapping(path = "/workdone")
public class WorkdoneController {
    @Autowired
    private WorkdoneService wdSvc;

    @Autowired
    private VentureService ventureService;

    @GetMapping
    public String createWd(Model model, @ModelAttribute Workdone workdone) {
        List<String> teamList = ventureService.getTeamNames();
        model.addAttribute("workdone", workdone);
        model.addAttribute("teamList", teamList);
        return "createWd";
    }

    @PostMapping
    public String showWd(Model model, @ModelAttribute @Valid Workdone workdone, BindingResult bindings) {
        List<String> teamList = ventureService.getTeamNames();
        if (bindings.hasErrors()) {
            System.out.println("Workdone creation >>>>> has error");
            model.addAttribute("teamList", teamList);
            return "createWd";
        }
        workdone.setWorkdoneId();
        wdSvc.save(workdone);
        return "workdone";
    }

    @GetMapping(path = "/all")
    public String showAllWd(Model model) {
        List<Workdone> workdones = wdSvc.findAllWorkdone();
        model.addAttribute("workdones", workdones);
        return "workdones";
    }

    @GetMapping(path = "{workdoneId}")
    public String getByWorkdoneId(Model model, @PathVariable String workdoneId) {
        Workdone workdone = wdSvc.findByWorkdoneId(workdoneId);
        model.addAttribute("workdone", workdone);
        return "workdone";
    }
}
