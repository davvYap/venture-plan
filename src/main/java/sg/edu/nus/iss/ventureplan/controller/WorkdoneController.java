package sg.edu.nus.iss.ventureplan.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private String tempWorkdoneId;

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

    // DELETE
    @GetMapping(path = "/delete/{workdoneId}")
    public String deleteWorkdoneById(Model model, @PathVariable String workdoneId) {
        System.out.println("Deleted workdone >>> " + workdoneId);
        wdSvc.deleteWorkdone(workdoneId);

        List<Workdone> workdones = wdSvc.findAllWorkdone();
        model.addAttribute("workdones", workdones);
        return "workdones";
        // return "customError";
    }

    // EDIT workdone
    @GetMapping(path = "/edit/{workdoneId}")
    public String editTeam(Model model, @PathVariable String workdoneId) {
        List<String> teamList = ventureService.getTeamNames();
        model.addAttribute("teamList", teamList);

        tempWorkdoneId = workdoneId;

        Workdone wd = wdSvc.findByWorkdoneId(workdoneId);
        // to populate DATE of workdone automatically
        LocalDate wdDate = wd.getDateOfWork();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = wdDate.format(formatter);
        LocalDate revWdDate = LocalDate.parse(formattedDate);
        System.out.println(revWdDate);
        wd.setDateOfWork(revWdDate);

        model.addAttribute("workdone", wd);

        return "editWd";
    }

    // EDIT workdone
    @PostMapping(path = "/edit")
    public String updateTeam(Model model, @ModelAttribute @Valid Workdone workdone, BindingResult bindings) {
        // delete old record
        System.out.println(tempWorkdoneId);
        wdSvc.deleteWorkdone(tempWorkdoneId);
        List<String> teamList = ventureService.getTeamNames();
        if (bindings.hasErrors()) {
            System.out.println("Workdone update >>>>> has error");
            model.addAttribute("teamList", teamList);
            return "editWd";
        }
        workdone.setWorkdoneId();
        if (!wdSvc.isUniqueWorkdone(workdone.getWorkdoneId())) {
            wdSvc.deleteWorkdone(workdone.getWorkdoneId());
        }
        wdSvc.save(workdone);
        System.out.printf("Workdone %s is updated !!! \n", workdone.getWorkdoneId());
        return "workdone";
    }

}
