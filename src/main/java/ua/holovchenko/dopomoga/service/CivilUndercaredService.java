package ua.holovchenko.dopomoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.holovchenko.dopomoga.entity.CivilUndercared;
import ua.holovchenko.dopomoga.repository.CivilUndercaredRepository;
import ua.holovchenko.dopomoga.repository.criteria.CivilUndercaredSpecification;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CivilUndercaredService {
    private final CivilUndercaredRepository repo;
    private final CivilUndercaredSpecification spec;

    @Autowired
    public CivilUndercaredService(CivilUndercaredRepository repo, CivilUndercaredSpecification spec) {
        this.repo = repo;
        this.spec = spec;
    }

    public Page<CivilUndercared> getCivilUndercaredPage(List<Filter> filterList,
                                                        List<DateFilter> dateLessThanList,
                                                        List<DateFilter> dateGreaterThanList,
                                                        Integer page,
                                                        Integer size) {
        if (page == null || page < 1)
            page = 1;

        if (size == null || size < 1)
            size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        return repo.findAll(spec.columnEqual(filterList)
                .and(spec.dateLessThan(dateLessThanList))
                .and(spec.dateGreaterThan(dateGreaterThanList)), pageable);
    }

    public CivilUndercared getCivilUndercaredByPassport(String passport) throws NoSuchElementException {
        return repo.findByPassport(passport).orElseThrow();
    }

    public CivilUndercared getCivilUndercaredByTaxpayerId(String taxpayerID) throws NoSuchElementException {
        return repo.findByTaxpayerID(taxpayerID).orElseThrow();
    }

    public CivilUndercared getCivilUndercaredByPhoneNumber(String phoneNumber) throws NoSuchElementException {
        return repo.findByPhoneNumber(phoneNumber).orElseThrow();
    }

    public CivilUndercared saveCivilUndercared(CivilUndercared civilUndercared) {
        return repo.save(civilUndercared);
    }

    public boolean exists(String id) {
        return repo.existsById(id);
    }
}
