package ua.holovchenko.dopomoga.controller.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.holovchenko.dopomoga.entity.CivilUndercared;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;
import ua.holovchenko.dopomoga.service.CivilUndercaredService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ui/civil-undercared")
public class CivilUndercaredUIController {
    private final CivilUndercaredService civilUndercaredService;

    @Autowired
    public CivilUndercaredUIController(CivilUndercaredService civilUndercaredService) {
        this.civilUndercaredService = civilUndercaredService;
    }

    @GetMapping
    public String getCivilUndercaredPage(Model model,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size,
                                         @RequestParam(required = false) String settlement,
                                         @RequestParam(required = false) Boolean isMobile,
                                         @RequestParam(required = false) Boolean hasCaretaker,
                                         @RequestParam(required = false) Boolean isDisabled,
                                         @RequestParam(required = false) Boolean isElderly,
                                         @RequestParam(required = false) Boolean hasManyChildren,
                                         @RequestParam(required = false) Boolean isIncurable,
                                         @RequestParam(required = false) Boolean isBedridden,
                                         @RequestParam(required = false) Boolean isLonely,
                                         @RequestParam(required = false) String minBirthDate,
                                         @RequestParam(required = false) String maxBirthDate,
                                         @RequestParam(required = false) String minLastTakenHelp,
                                         @RequestParam(required = false) String maxLastTakenHelp) {
        List<Filter> filters = new ArrayList<>();
        List<DateFilter> dateLessThanList = new ArrayList<>();
        List<DateFilter> dateGreaterThanList = new ArrayList<>();

        if (settlement != null && !settlement.isEmpty()) {
            filters.add(new Filter("settlement", settlement));
        }
        if (isMobile != null) {
            filters.add(new Filter("isMobile", isMobile));
        }
        if (hasCaretaker != null) {
            filters.add(new Filter("hasCaretaker", hasCaretaker));
        }
        if (isDisabled != null) {
            filters.add(new Filter("isDisabled", isDisabled));
        }
        if (isElderly != null) {
            filters.add(new Filter("isElderly", isElderly));
        }
        if (hasManyChildren != null) {
            filters.add(new Filter("hasManyChildren", hasManyChildren));
        }
        if (isIncurable != null) {
            filters.add(new Filter("isIncurable", isIncurable));
        }
        if (isBedridden != null) {
            filters.add(new Filter("isBedridden", isBedridden));
        }
        if (isLonely != null) {
            filters.add(new Filter("isLonely", isLonely));
        }
        if (maxBirthDate != null && !maxBirthDate.isEmpty()) {
            dateLessThanList.add(new DateFilter("birthDate", Date.valueOf(maxBirthDate)));
        }
        if (maxLastTakenHelp != null && !maxLastTakenHelp.isEmpty()) {
            dateLessThanList.add(new DateFilter("lastTakenHelp", Date.valueOf(maxLastTakenHelp)));
        }
        if (minBirthDate != null && !minBirthDate.isEmpty()) {
            dateGreaterThanList.add(new DateFilter("birthDate", Date.valueOf(minBirthDate)));
        }
        if (minLastTakenHelp != null && !minLastTakenHelp.isEmpty()) {
            dateGreaterThanList.add(new DateFilter("lastTakenHelp", Date.valueOf(minLastTakenHelp)));
        }

        model.addAttribute("civilUndercaredPage", civilUndercaredService
                .getCivilUndercaredPage(filters, dateLessThanList, dateGreaterThanList, page, size));
        return "/civilundercared/page";
    }

    @GetMapping("/{id}")
    public String getCivilUndercaredById(@PathVariable String id, Model model) {
        try {
            model.addAttribute("civilUndercared", civilUndercaredService.getCivilUndercaredByTaxpayerId(id));
            return "/civilundercared/one";
        } catch (NoSuchElementException e) {
            return "/civilundercared/notfound";
        }
    }

    @GetMapping("/by-pass/{passport}")
    public String getCivilUndercaredByPassport(@PathVariable String passport, Model model) {
        try {
            model.addAttribute("civilUndercared", civilUndercaredService.getCivilUndercaredByPassport(passport));
            return "/civilundercared/one";
        } catch (NoSuchElementException e) {
            return "civilundercared/notfound";
        }
    }

    @GetMapping("/by-phone/{phone}")
    public String getCivilUndercaredByPhoneNumber(@PathVariable String phone, Model model) {
        try {
            model.addAttribute("civilUndercared", civilUndercaredService.getCivilUndercaredByPhoneNumber(phone));
            return "/civilundercared/one";
        } catch (NoSuchElementException e) {
            return "civilundercared/notfound";
        }
    }

    @GetMapping("/new")
    public String createCivilUndercaredForm(Model model) {
        model.addAttribute("civilUndercared", new CivilUndercared());
        return "/civilundercared/create";
    }

    @PostMapping("/new")
    public String createCivilUndercared(@ModelAttribute CivilUndercared civilUndercared, Model model) {
        if (civilUndercaredService.exists(civilUndercared.getTaxpayerID())) {
            model.addAttribute("civilUndercared", civilUndercaredService.getCivilUndercaredByTaxpayerId(civilUndercared.getTaxpayerID()));
            return "/civilundercared/edit";
        }
        model.addAttribute("civilUndercared", civilUndercaredService.saveCivilUndercared(civilUndercared));
        return "/civilundercared/one";
    }

    @GetMapping("/{id}/edit")
    public String editCivilUndercaredForm(Model model, @PathVariable String id) {
        try {
            model.addAttribute("civilUndercared", civilUndercaredService.getCivilUndercaredByTaxpayerId(id));
            return "/civilundercared/edit";
        } catch (NoSuchElementException e) {
            return "civilundercared/notfound";
        }
    }

    @PostMapping("/{id}/edit")
    public String editCivilUndercared(Model model, @PathVariable String id, @ModelAttribute CivilUndercared civilUndercared) {
        model.addAttribute("civilUndercared", civilUndercaredService.saveCivilUndercared(civilUndercared));
        return "/civilundercared/one";
    }
}
