package ua.holovchenko.dopomoga.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.holovchenko.dopomoga.entity.CivilRequest;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;
import ua.holovchenko.dopomoga.service.CivilRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/civilrequests")
public class CivilRequestRESTController {

    private final CivilRequestService civilRequestService;

    @Autowired
    public CivilRequestRESTController(CivilRequestService civilRequestService) {
        this.civilRequestService = civilRequestService;
    }

    @GetMapping("")
    public ResponseEntity<Page<CivilRequest>> getCivilRequestPage(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
        return ResponseEntity.ok().body(civilRequestService.getCivilRequestPage(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), page, size));
    }

    @PostMapping("")
    public ResponseEntity<Page<CivilRequest>> getCivilRequestPageFiltered(
            @RequestBody List<Filter> filters,
            @RequestBody List<DateFilter> dateLessThanList,
            @RequestBody List<DateFilter> dateGreaterThanList,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size) {
        return ResponseEntity.ok().body(civilRequestService
                .getCivilRequestPage(filters, dateLessThanList, dateGreaterThanList, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CivilRequest> getCivilRequestByID(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(civilRequestService.getCivilRequestByID(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<CivilRequest> editCivilRequest(@PathVariable String id, @RequestBody CivilRequest civilRequest) {
        try {
            return ResponseEntity.ok().body(civilRequestService.saveCivilRequest(civilRequest));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<CivilRequest> createCivilRequest(@RequestBody CivilRequest civilRequest) {
        return ResponseEntity.ok().body(civilRequestService.saveCivilRequest(civilRequest));
    }
}
