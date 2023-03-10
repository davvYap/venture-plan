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

import jakarta.validation.Valid;
import sg.edu.nus.iss.ventureplan.model.Cost;
import sg.edu.nus.iss.ventureplan.service.CostService;

@Controller
@RequestMapping(path = "/cost")
public class CostController {
    @Autowired
    private CostService costSvc;

    private String oldVersion;

    @GetMapping
    public String mainPage() {
        return "costMain";
    }

    @GetMapping(path = "/create")
    public String createCost(Model model, @ModelAttribute Cost cost) {
        model.addAttribute("cost", cost);
        return "createCost";
    }

    @PostMapping(path = "/create")
    public String showCost(Model model, @ModelAttribute @Valid Cost cost, BindingResult bindings) {
        if (bindings.hasErrors()) {
            System.out.println("Cost creation >>>>> has error");
            return "createCost";
        }
        // check if version exist in database
        if (!costSvc.isUniqueVersion(cost.getVersion())) {
            ObjectError err = new ObjectError("globalError",
                    "Cost version %s exists in database.".formatted(cost.getVersion()));
            bindings.addError(err);
            return "createCost";
        }
        costSvc.save(cost);

        model.addAttribute("version", cost.getVersion());
        return "cost";
    }

    @GetMapping(path = "/all")
    public String showAllTeams(Model model) {
        List<Cost> allCost = costSvc.getAllCost();
        model.addAttribute("allCost", allCost);
        return "costs";
    }

    @GetMapping(path = "{version}")
    public String getTeamByPathVar(Model model, @PathVariable String version) {
        Cost selectedCost = costSvc.findByVersion(version);
        model.addAttribute("cost", selectedCost);
        return "cost";
    }

    // DELETE
    @GetMapping(path = "/delete/{version}")
    public String deleteByVersion(Model model, @PathVariable String version) {
        System.out.println("Deleted cost version >>> " + version);
        costSvc.deleteCost(version);

        List<Cost> allCost = costSvc.getAllCost();
        model.addAttribute("allCost", allCost);
        return "costs";
    }

    // EDIT cost
    @GetMapping(path = "/edit/{version}")
    public String editCost(Model model, @PathVariable String version) {
        Cost cost = costSvc.findByVersion(version);
        model.addAttribute("cost", cost);

        oldVersion = version;
        return "editCost";
    }

    // EDIT team
    @PostMapping(path = "/edit")
    public String updateTeam(Model model, @ModelAttribute @Valid Cost cost, BindingResult bindings) {
        if (bindings.hasErrors()) {
            System.out.println("Cost update >>>>> has error");
            return "editCost";
        }
        // delete old version
        costSvc.deleteCost(oldVersion);

        if (!costSvc.isUniqueVersion(cost.getVersion())) {
            costSvc.deleteCost(cost.getVersion());
        }
        model.addAttribute("costVersion", cost.getVersion());

        costSvc.save(cost);
        System.out.printf("Cost version %s is updated !!! \n", cost.getVersion());
        return "cost";
    }
}
