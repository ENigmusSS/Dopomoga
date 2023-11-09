package ua.holovchenko.dopomoga.controller.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
                                      @RequestParam(required = false) int page,
                                      @RequestParam(required = false) int size,
                                      @RequestParam(required = false) String requestedById,
                                      @RequestParam(required = false) String requestStatus,
                                      @RequestParam(required = false) Date maxReceivedDate,
                                      @RequestParam(required = false) Date minReceivedDate,
                                      @RequestParam(required = false) Date maxChangedDate,
                                      @RequestParam(required = false) Date minChangedDate) {
        List<Filter> filters = new ArrayList<>();
        List<DateFilter> dateLessThanList = new ArrayList<>();
        List<DateFilter> dateGreaterThanList = new ArrayList<>();

        if (requestedById != null) {
            model.addAttribute("requestedById", requestedById);
            filters.add(new Filter("requestedBy", civilUndercaredService.getCivilUndercaredByTaxpayerId(requestedById)));
        }
        if (requestStatus != null) {
            filters.add(new Filter("status", RequestStatus.valueOf(requestStatus)));
        }
        if (maxReceivedDate != null) {
            dateLessThanList.add(new DateFilter("receivedDate", maxReceivedDate));
        }
        if (minReceivedDate != null) {
            dateGreaterThanList.add(new DateFilter("receivedDate", minReceivedDate));
        }
        if (maxChangedDate != null) {
            dateLessThanList.add(new DateFilter("lastChanged", maxChangedDate));
        }
        if (minChangedDate != null) {
            dateGreaterThanList.add(new DateFilter("lastChanged", minChangedDate));
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
            model.addAttribute("requestedBy", civilRequest.getRequestedBy().getTaxpayerID());
            return "/civilrequests/edit";
        } catch (NoSuchElementException e) {
            return "/civilrequests/notfound";
        }
    }

    @PostMapping("/{id}/edit")
    public String editCivilRequest(@PathVariable String id, @ModelAttribute CivilRequest civilRequest, @ModelAttribute String requestedBy, Model model) {
        civilRequest.setRequestedBy(civilUndercaredService.getCivilUndercaredByTaxpayerId(requestedBy));
        model.addAttribute("civilRequest", civilRequestService.saveCivilRequest(civilRequest));
        return "/civilrequests/one";
    }

    @GetMapping("/new")
    public String createCivilRequestForm(Model model) {
        model.addAttribute("civilRequest", new CivilRequest());
        model.addAttribute("requestedBy", "");
        return "civilrequests/create";
    }

    @PostMapping("/new")
    public String createCivilRequest(@ModelAttribute CivilRequest civilRequest, @ModelAttribute String requestedBy, Model model) {
        civilRequest.setRequestedBy(civilUndercaredService.getCivilUndercaredByTaxpayerId(requestedBy));
        model.addAttribute("civilRequest", civilRequestService.saveCivilRequest(civilRequest));
        return "civilrequests/one";
    }
}
