package ua.holovchenko.dopomoga.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.holovchenko.dopomoga.entity.CivilUndercared;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;
import ua.holovchenko.dopomoga.service.CivilUndercaredService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/civilundercared")
public class CivilUndercaredRESTController {
    private final CivilUndercaredService civilUndercaredService;

    @Autowired
    public CivilUndercaredRESTController(CivilUndercaredService civilUndercaredService) {
        this.civilUndercaredService = civilUndercaredService;
    }

    @GetMapping("")
    public ResponseEntity<Page<CivilUndercared>> getCivilUndercaredPage(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
        return ResponseEntity.ok().body(civilUndercaredService.getCivilUndercaredPage(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), page, size));
    }

    @PostMapping("")
    public ResponseEntity<Page<CivilUndercared>> getCivilUndercaredPageFiltered(
            @RequestBody List<Filter> filters,
            @RequestBody List<DateFilter> dateLessThanList,
            @RequestBody List<DateFilter> dateGreaterThanList,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size) {
        return ResponseEntity.ok().body(civilUndercaredService
                .getCivilUndercaredPage(filters, dateLessThanList, dateGreaterThanList, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CivilUndercared> getCivilUndercaredById(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(civilUndercaredService.getCivilUndercaredByTaxpayerId(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-pass/{passport}")
    public ResponseEntity<CivilUndercared> getCivilUndercaredByPass(@PathVariable String passport) {
        try {
            return ResponseEntity.ok().body(civilUndercaredService.getCivilUndercaredByPassport(passport));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-phone/{phone}")
    public ResponseEntity<CivilUndercared> getCivilUndercaredByPhoneNumber(@PathVariable String phone) {
        try {
            return ResponseEntity.ok().body(civilUndercaredService.getCivilUndercaredByPhoneNumber(phone));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<CivilUndercared> editCivilUndercared(@PathVariable String id, @RequestBody CivilUndercared civilUndercared) {
        try {
            if (!civilUndercared.getTaxpayerID().equals(id)) {
                return ResponseEntity.badRequest().body(civilUndercaredService.getCivilUndercaredByTaxpayerId(id));
            }
            return ResponseEntity.ok().body(civilUndercaredService.saveCivilUndercared(civilUndercared));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<CivilUndercared> createCivilUndercared(@RequestBody CivilUndercared civilUndercared) {
        if (civilUndercaredService.exists(civilUndercared.getTaxpayerID())) {
            return ResponseEntity.badRequest().body(civilUndercaredService.getCivilUndercaredByTaxpayerId(civilUndercared.getTaxpayerID()));
        }
        return ResponseEntity.ok().body(civilUndercaredService.saveCivilUndercared(civilUndercared));
    }
}
