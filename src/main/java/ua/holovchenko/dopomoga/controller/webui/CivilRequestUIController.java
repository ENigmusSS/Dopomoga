package ua.holovchenko.dopomoga.controller.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import ua.holovchenko.dopomoga.entity.CivilRequest;
import ua.holovchenko.dopomoga.entity.RequestStatus;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;
import ua.holovchenko.dopomoga.service.CivilRequestService;
import ua.holovchenko.dopomoga.service.CivilUndercaredService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ui/civil-requests")
public class CivilRequestUIController {
    private final CivilRequestService civilRequestService;
    private final CivilUndercaredService civilUndercaredService;

    @Autowired
    public CivilRequestUIController(CivilRequestService civilRequestService, CivilUndercaredService civilUndercaredService) {
        this.civilRequestService = civilRequestService;
        this.civilUndercaredService = civilUndercaredService;
    }

    @GetMapping("")
    public String getCivilRequestPage(Model model,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size,
                                      @RequestParam(required = false) String requestedById,
                                      @RequestParam(required = false) String requestStatus,
                                      @RequestParam(required = false) String maxReceivedDate,
                                      @RequestParam(required = false) String minReceivedDate,
                                      @RequestParam(required = false) String maxChangedDate,
                                      @RequestParam(required = false) String minChangedDate) {
        List<Filter> filters = new ArrayList<>();
        List<DateFilter> dateLessThanList = new ArrayList<>();
        List<DateFilter> dateGreaterThanList = new ArrayList<>();

        if (requestedById != null && !requestedById.isEmpty()) {
            model.addAttribute("requestedById", requestedById);
            try {
                filters.add(new Filter("requestedBy", civilUndercaredService.getCivilUndercaredByTaxpayerId(requestedById)));
            } catch (NoSuchElementException e) {
                return "/civilundercared/notfound";
            }
        }
        if (requestStatus != null && !requestStatus.equals("ANY")) {
            filters.add(new Filter("status", RequestStatus.valueOf(requestStatus)));
        }
        if (maxReceivedDate != null && !maxReceivedDate.isEmpty()) {
            dateLessThanList.add(new DateFilter("receivedDate", Date.valueOf(maxReceivedDate)));
        }
        if (minReceivedDate != null && !minReceivedDate.isEmpty()) {
            dateGreaterThanList.add(new DateFilter("receivedDate", Date.valueOf(minReceivedDate)));
        }
        if (maxChangedDate != null && !maxChangedDate.isEmpty()) {
            dateLessThanList.add(new DateFilter("lastChanged",Date.valueOf(maxChangedDate)));
        }
        if (minChangedDate != null && !minChangedDate.isEmpty()) {
            dateGreaterThanList.add(new DateFilter("lastChanged", Date.valueOf(minChangedDate)));
        }
        model.addAttribute("civilRequestPage", civilRequestService
                .getCivilRequestPage(filters, dateLessThanList, dateGreaterThanList, page, size));
        return "/civilrequests/page";
    }

    @GetMapping("/{id}")
    public String getCivilRequestByID(@PathVariable String id, Model model) {
        try {
            model.addAttribute("civilRequest", civilRequestService.getCivilRequestByID(id));
            return "/civilrequests/one";
        } catch (NoSuchElementException e) {
            return "/civilrequests/notfound";
        }
    }

    @GetMapping("/{id}/edit")
    public String editCivilRequestForm(@PathVariable String id, Model model) {
        try {
            CivilRequest civilRequest = civilRequestService.getCivilRequestByID(id);
            model.addAttribute("civilRequest", civilRequest);
            model.addAttribute("requestBy", civilRequest.getRequestedBy().getTaxpayerID());
            model.addAttribute("statuses", civilRequest.getStatus());
            return "/civilrequests/edit";
        } catch (NoSuchElementException e) {
            return "/civilrequests/notfound";
        }
    }

    @PostMapping("/{id}/edit")
    public String editCivilRequest(@PathVariable String id, @ModelAttribute CivilRequest civilRequest, @ModelAttribute String requestBy, Model model) {
        civilRequest.setRequestedBy(civilUndercaredService.getCivilUndercaredByTaxpayerId(requestBy));
        model.addAttribute("civilRequest", civilRequestService.saveCivilRequest(civilRequest));
        return "/civilrequests/one";
    }

    @GetMapping("/new")
    public String createCivilRequestForm(Model model) {
        model.addAttribute("civilRequest", new CivilRequest());
        model.addAttribute("statuses", RequestStatus.PAUSED);
        return "civilrequests/create";
    }

    @PostMapping("/new")
    public String createCivilRequest(@ModelAttribute CivilRequest civilRequest, Model model) {
        try {
            if (civilRequest.getRequestedBy() == null) {
                return "civilundercared/notfound";
            } else {
            model.addAttribute("civilRequest", civilRequestService.saveCivilRequest(civilRequest));
            return "civilrequests/one";}
        }  catch (TemplateInputException e) {
        return "civilundercared/notfound";
    }
    }
}
